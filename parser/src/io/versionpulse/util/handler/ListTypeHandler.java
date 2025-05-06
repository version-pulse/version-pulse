package io.versionpulse.util.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.versionpulse.common.ObjectMapperProvider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ListTypeHandler implements TypeHandler {
    private static final ObjectMapper objectMapper = ObjectMapperProvider.get();
    private static final ListTypeHandler INSTANCE = new ListTypeHandler();

    private ListTypeHandler() {}

    public static ListTypeHandler getInstance() {
        return INSTANCE;
    }

    public JsonNode handle(Type type) {
        ArrayNode node = objectMapper.createArrayNode();

        if (type instanceof ParameterizedType parameterizedType) {
            Type[] actualTypes = parameterizedType.getActualTypeArguments();
            if (actualTypes.length == 1) {
                Type elementType = actualTypes[0];
                TypeHandler handler = TypeHandlerFactory.getHandler(elementType);
                node.add(handler.handle(elementType));
            }
        }
        return node;
    }
}
