package io.versionpulse.util.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.versionpulse.common.ObjectMapperProvider;
import io.versionpulse.util.TypeCategory;
import io.versionpulse.util.TypeChecker;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class MapTypeHandler implements TypeHandler {
    private static final ObjectMapper objectMapper = ObjectMapperProvider.get();
    private static final MapTypeHandler INSTANCE = new MapTypeHandler();

    private MapTypeHandler() {}

    public static MapTypeHandler getInstance() {
        return INSTANCE;
    }

    public JsonNode handle(Type type) {
        ObjectNode node = objectMapper.createObjectNode();

        if (type instanceof ParameterizedType parameterizedType) {
            Type[] actualTypes = parameterizedType.getActualTypeArguments();

            if (actualTypes.length == 2) {
                // key
                Type keyType = actualTypes[0];
                String keyName = ((Class<?>) keyType).getSimpleName();
                // value
                Type valueType = actualTypes[1];
                TypeHandler handler = TypeHandlerFactory.getHandler(valueType);
                JsonNode valueNode = handler.handle(valueType);
                node.set(keyName, valueNode);
            }
        }
        return node;
    }
}
