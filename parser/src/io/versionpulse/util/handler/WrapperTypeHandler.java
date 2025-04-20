package io.versionpulse.util.handler;

import com.fasterxml.jackson.databind.node.TextNode;

public class WrapperTypeHandler implements TypeHandler {
    @Override
    public TextNode handle(Class<?> clazz) {
        return new TextNode(clazz.getSimpleName());
    }
}