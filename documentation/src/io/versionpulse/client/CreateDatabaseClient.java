package io.versionpulse.client;

import org.springframework.http.HttpHeaders;

import io.versionpulse.dto.CreateDTO;
import io.versionpulse.util.WebClientManager;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class CreateDatabaseClient {
	private String notionKey;
    private String pageId;
    private final String url = "https://api.notion.com/v1/databases";

    public void execute() {
        // WebClient 인스턴스 생성
        WebClientManager webManager = new WebClientManager(url);

        // 요청 헤더 설정
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        header.add("Authorization", "Bearer " + notionKey);
        header.add("Notion-Version", "2022-06-28");

        // 요청 본문 생성
        CreateDTO requestBody = CreateDTO.of(pageId);
        
        String response = webManager.syncRequest(url, header, requestBody, String.class);

        System.out.println("최종 API 응답: " + response);
    }
}
