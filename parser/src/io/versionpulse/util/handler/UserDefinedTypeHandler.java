package io.versionpulse.util.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.lang.reflect.Field;

public class UserDefinedTypeHandler implements TypeHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public JsonNode handle(Class<?> clazz) throws Exception {
        ObjectNode node = objectMapper.createObjectNode();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            Class<?> fieldType = field.getType();

            TypeHandler handler = TypeHandlerFactory.getHandler(fieldType);
            node.set(name, handler.handle(fieldType));
        }

        return node;
    }
}
