package io.versionpulse.client;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import io.versionpulse.dto.UpdateDTO;
import io.versionpulse.model.dto.ApiDto;
import io.versionpulse.model.dto.ApiGroupDto;
import io.versionpulse.util.CodeFormatter;
import io.versionpulse.util.HeadingFormatter;
import io.versionpulse.util.TableFormatter;
import io.versionpulse.util.WebClientManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import reactor.core.publisher.Mono;

@Slf4j
@Builder
@AllArgsConstructor
public class UpdateDatabaseClient {
	private String notionKey;
	private String databaseId;
	private final String url = "https://api.notion.com/v1/pages";
	private List<ApiGroupDto> records;

	public void execute() {
		// CPU 코어 수에 맞춰 스레드 풀 생성
		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		try {
			for (ApiGroupDto group : records) {
				for (ApiDto api : group.apis()) {
					String groupTag = group.groupTag();
					executor.submit(() -> sendSingleRequest(groupTag, api));
				}
			}
		} finally {
			// 더 이상 작업을 제출하지 않음
			executor.shutdown();
			try {
				// 모든 작업이 끝날 때까지 대기 (최대 60초)
				if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
					executor.shutdownNow(); // 시간 초과 시 강제 종료
				}
			} catch (InterruptedException e) {
				executor.shutdownNow();
				Thread.currentThread().interrupt();
			}
		}
	}

	public void sendSingleRequest(String groupTag, ApiDto api) {
		WebClientManager webManager = new WebClientManager(url);

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		header.add("Authorization", "Bearer " + notionKey);
		header.add("Notion-Version", "2022-06-28");

		UpdateDTO requestBody = UpdateDTO.of(databaseId, api.name(), api.description(), api.apiSchemaDto().path(),
				api.apiSchemaDto().method(), HeadingFormatter.toHeading("Query String"),
				TableFormatter.toTable(api.apiSchemaDto().queryString()), HeadingFormatter.toHeading("Parameter"),
				TableFormatter.toTable(api.apiSchemaDto().parameter()), HeadingFormatter.toHeading("RequestBody"),
				CodeFormatter.toCode(api.apiSchemaDto().requestBody().json()),
				HeadingFormatter.toHeading("ResponseBody"),
				CodeFormatter.toCode(api.apiSchemaDto().responseBody().json()));

		Mono<String> response = webManager.asyncRequest(url, header, requestBody, String.class);

		// 비동기 작업이 완료될 때까지 메인 스레드 대기
		CountDownLatch latch = new CountDownLatch(1);
		response.subscribe(result -> {
			log.info("Update a API specitication"+" : "+result);
			latch.countDown(); // 비동기 작업이 완료되면 카운트다운
		}, error -> {
			log.warn("오류 발생: " + error.getMessage());
			latch.countDown(); // 오류 발생시에도 카운트다운
		});

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
