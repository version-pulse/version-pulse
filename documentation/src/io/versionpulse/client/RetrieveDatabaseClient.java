package io.versionpulse.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.versionpulse.util.WebClientManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpHeaders;

@Builder
@AllArgsConstructor
public class RetrieveDatabaseClient {
    private String notionKey;
    private String pageId;
    private final String url = "https://api.notion.com/v1/blocks/";

    public String execute() {
        String requestUrl = url + pageId + "/children";

        WebClientManager webManager = new WebClientManager(requestUrl);

        // 요청 헤더 설정
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        header.add("Authorization", "Bearer " + notionKey);
        header.add("Notion-Version", "2022-06-28");


        String response = webManager.syncRequest(requestUrl, header, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);
            JsonNode results = root.get("results");
            for (JsonNode block : results) {
                String type = block.get("type").asText();
                if ("child_database".equals(type)) {
                    return block.get("id").asText();
                }
            }
            return null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
