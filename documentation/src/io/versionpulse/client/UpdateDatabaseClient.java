package io.versionpulse.client;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

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

@Builder
@AllArgsConstructor
public class UpdateDatabaseClient {
	private static final Logger logger = Logger.getLogger(UpdateDatabaseClient.class.getName());
	private String notionKey;
	private String databaseId;
	private final String url = "https://api.notion.com/v1/pages";
	private List<ApiGroupDto> records;

	public void execute() {
		for (ApiGroupDto group : records) {
			for (ApiDto api : group.apis()) {
				sendSingleRequest(group.groupTag(), api);
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
			logger.info("Update a API specitication"+" : "+result);
			latch.countDown(); // 비동기 작업이 완료되면 카운트다운
		}, error -> {
			logger.warning("오류 발생: " + error.getMessage());
			latch.countDown(); // 오류 발생시에도 카운트다운
		});

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
