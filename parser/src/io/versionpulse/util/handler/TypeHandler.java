package io.versionpulse.util.handler;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

public interface TypeHandler {
    JsonNode handle(Type type) throws Exception;

    default JsonNode handle(Class<?> clazz) {
        throw new UnsupportedOperationException(clazz+": Class<?> not supported. Use handle(Type type) instead.");
    }
}
