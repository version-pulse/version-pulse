package io.versionpulse.util.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class PrimitiveTypeHandler implements TypeHandler{
    @Override
    public JsonNode handle(Class<?> clazz) throws Exception {
        return new TextNode(clazz.getName());
    }
}
