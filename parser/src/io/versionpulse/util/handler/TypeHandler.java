package io.versionpulse.util.handler;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

public interface TypeHandler {
    JsonNode handle(Type type) ;

    // 메서드 시그니처 변경에 따른 이전 버전과의 호환성을 지키기 위함.
    default JsonNode handle(Class<?> clazz) {
        throw new UnsupportedOperationException(clazz+": Class<?> not supported. Use handle(Type type) instead.");
    }
}
