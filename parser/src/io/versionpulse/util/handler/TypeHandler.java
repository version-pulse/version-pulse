package io.versionpulse.util.handler;

import com.fasterxml.jackson.databind.JsonNode;

public interface TypeHandler {
    JsonNode handle(Class<?> clazz) throws Exception;
}
