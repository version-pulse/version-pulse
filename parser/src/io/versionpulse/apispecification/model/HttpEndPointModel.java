package io.versionpulse.apispecification.model;

import org.springframework.http.HttpMethod;

public record HttpEndPointModel(HttpMethod httpMethod, String path) {

}
