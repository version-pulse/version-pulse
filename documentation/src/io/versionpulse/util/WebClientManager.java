package io.versionpulse.util;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

public class WebClientManager {

    private final WebClient webClient;

    public WebClientManager(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    // 비동기 요청 처리
    public <T> Mono<T> asyncRequest(String uri, HttpHeaders headers, Object requestBody, Class<T> responseType) {
        return webClient.post()
                .uri(uri)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(responseType)
                .doOnTerminate(() -> System.out.println("API 호출 완료"))
                .doOnError(error -> System.out.println("API 호출 중 오류 발생: " + error.getMessage()));
    }

    // 동기 요청 처리
    public <T> T syncRequest(String uri, HttpHeaders headers, Object requestBody, Class<T> responseType) {
        return asyncRequest(uri, headers, requestBody, responseType)
                .block();
    }
}
