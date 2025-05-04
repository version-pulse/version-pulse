package io.versionpulse.util.handler;

import io.versionpulse.util.TypeChecker;

import java.lang.reflect.Type;

public class TypeHandlerFactory {

    public static TypeHandler getHandler(Type type) {
        return switch (TypeChecker.check(type)) {
            case PRIMITIVE -> new PrimitiveTypeHandler();
            case WRAPPER -> new WrapperTypeHandler();
            case LIST -> new ListTypeHandler();
            case MAP -> new MapTypeHandler();
            case USER_DEFINED -> new UserDefinedTypeHandler();
        };
    }
}
