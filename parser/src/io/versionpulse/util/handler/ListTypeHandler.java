package io.versionpulse.util.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ListTypeHandler implements TypeHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public JsonNode handle(Class<?> clazz) {
        throw new UnsupportedOperationException("Class<?> not supported. Use handle(Type type) instead.");
    }

    public JsonNode handle(Type type) throws Exception {
        ArrayNode array = objectMapper.createArrayNode();
        if (type instanceof ParameterizedType pt) {
            Type[] actualTypes = pt.getActualTypeArguments();
            if (actualTypes.length == 1 && actualTypes[0] instanceof Class<?> elementType) {
                TypeHandler handler = TypeHandlerFactory.getHandler(elementType);
                array.add(handler.handle(elementType));
            }
        }
        return array;
    }
}
