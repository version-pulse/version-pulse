package io.versionpulse.util.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class MapTypeHandler implements TypeHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public JsonNode handle(Class<?> clazz) {
        throw new UnsupportedOperationException("Class<?> not supported. Use handle(Type type) instead.");
    }
    public JsonNode handle(Type type) throws Exception {
        ObjectNode node = objectMapper.createObjectNode();

        if (type instanceof ParameterizedType pt) {
            Type rawType = pt.getRawType();
            // Map 타입 확인
            if (rawType instanceof Class<?> rawClass && Map.class.isAssignableFrom(rawClass)) {
                Type[] actualTypes = pt.getActualTypeArguments();

                if (actualTypes.length == 2) {
                    String keyType = ((Class<?>) actualTypes[0]).getSimpleName();
                    JsonNode valueType;
                    TypeHandler handler = TypeHandlerFactory.getHandler(((Class<?>) actualTypes[1]));

                    if (handler instanceof ListTypeHandler listTypeHandler) {
                        valueType = listTypeHandler.handle(actualTypes[1]);
                    }
                    else if (handler instanceof MapTypeHandler mapTypeHandler) {
                        valueType = mapTypeHandler.handle(actualTypes[1]);
                    }
                    else {
                        valueType = handler.handle(((Class<?>) actualTypes[1]));
                    }
                    node.set(keyType, valueType);
                }
            }
        }
        return node;
    }
}
