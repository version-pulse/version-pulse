package io.versionpulse.util.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.lang.reflect.Type;

public class PrimitiveTypeHandler implements TypeHandler{
    private static final PrimitiveTypeHandler INSTANCE = new PrimitiveTypeHandler();

    private PrimitiveTypeHandler() {}

    public static PrimitiveTypeHandler getInstance() {
        return INSTANCE;
    }
    @Override
    public JsonNode handle(Type type) {
        return new TextNode(type.getTypeName());
    }
}
