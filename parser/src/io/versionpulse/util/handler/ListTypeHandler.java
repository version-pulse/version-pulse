package io.versionpulse.util.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ListTypeHandler implements TypeHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode handle(Type type) {
        ArrayNode array = objectMapper.createArrayNode();
        if (type instanceof ParameterizedType pt) {
            Type[] actualTypes = pt.getActualTypeArguments();
            if (actualTypes.length == 1) {
                TypeHandler handler = TypeHandlerFactory.getHandler(actualTypes[0]);
                array.add(handler.handle(actualTypes[0]));
            }
        }
        return array;
    }
}
