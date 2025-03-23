package io.versionpulse.client;

import java.util.concurrent.CountDownLatch;

import org.springframework.http.HttpHeaders;

import io.versionpulse.dto.UpdateDTO;
import io.versionpulse.util.WebClientManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import reactor.core.publisher.Mono;

@Builder
@AllArgsConstructor
public class UpdateDatabaseClient {
	private String notionKey;
	private String databaseId;
	private final String url = "https://api.notion.com/v1/pages";
	
	public void execute() {
		WebClientManager webManager = new WebClientManager(url);
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
        header.add("Authorization", "Bearer "+notionKey);
        header.add("Notion-Version", "2022-06-28");
        
        UpdateDTO requestBody = UpdateDTO.of(databaseId, "API이름", "API설명", "API경로", "GET");
        
        Mono<String> response = webManager.asyncRequest(url, header, requestBody, String.class);
        
        // 비동기 작업이 완료될 때까지 메인 스레드 대기
        CountDownLatch latch = new CountDownLatch(1);
        response.subscribe(result -> {
            System.out.println("최종 API 응답: " + result);
            latch.countDown();  // 비동기 작업이 완료되면 카운트다운
        }, error -> {
            System.out.println("오류 발생: " + error.getMessage());
            latch.countDown();  // 오류 발생시에도 카운트다운
        });

        try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
