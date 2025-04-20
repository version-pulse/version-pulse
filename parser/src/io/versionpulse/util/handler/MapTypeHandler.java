package io.versionpulse.util.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class MapTypeHandler implements TypeHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public JsonNode handle(Class<?> clazz) {
        throw new UnsupportedOperationException("Class<?> not supported. Use handle(Type type) instead.");
    }
    public JsonNode handle(Type type) throws Exception {
        ObjectNode node = objectMapper.createObjectNode();

        for (Field field : type.getClass().getDeclaredFields()) {
            String name = field.getName();
            Class<?> fieldType = field.getType();
            TypeHandler handler = TypeHandlerFactory.getHandler(fieldType);
            node.set(name, handler.handle(fieldType));
        }

        return node;
    }
}
