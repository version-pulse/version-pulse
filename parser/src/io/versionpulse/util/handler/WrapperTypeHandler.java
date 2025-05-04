package io.versionpulse.util.handler;

import com.fasterxml.jackson.databind.node.TextNode;

import java.lang.reflect.Type;

public class WrapperTypeHandler implements TypeHandler {
    @Override
    public TextNode handle(Type type) {
        String typeName = type.getTypeName();
        return new TextNode(typeName.substring(typeName.lastIndexOf('.') + 1));
    }
}