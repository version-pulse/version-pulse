package io.versionpulse.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Slf4j
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
                .doOnTerminate(() -> log.info("called an API..."))
                .doOnError(error -> log.warn("Error occur during calling notion api: " + error.getMessage()));
    }

    // 비동기 요청 처리
    public <T> Mono<T> asyncRequest(String uri, HttpHeaders headers, Class<T> responseType) {
        return webClient.get()
                .uri(uri)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(responseType)
                .doOnTerminate(() -> log.info("called an API..."))
                .doOnError(error -> log.warn("Error occur during calling notion api: " + error.getMessage()));
    }

    // 동기 요청 처리
    public <T> T syncRequest(String uri, HttpHeaders headers, Object requestBody, Class<T> responseType) {
        return asyncRequest(uri, headers, requestBody, responseType)
                .block();
    }

    public <T> T syncRequest(String uri, HttpHeaders headers, Class<T> responseType) {
        return asyncRequest(uri, headers, responseType)
                .block();
    }
}
