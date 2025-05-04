package io.versionpulse.util.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.lang.reflect.Type;

public class PrimitiveTypeHandler implements TypeHandler{
    @Override
    public JsonNode handle(Type type) throws  Exception {
        return new TextNode(type.getTypeName());
    }
}
