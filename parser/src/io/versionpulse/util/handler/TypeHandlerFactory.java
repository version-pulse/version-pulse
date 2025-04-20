package io.versionpulse.util.handler;

import io.versionpulse.util.ClassType;
import io.versionpulse.util.TypeChecker;

public class TypeHandlerFactory {

    public static TypeHandler getHandler(Class<?> clazz) {
        return switch (TypeChecker.check(clazz)) {
            case PRIMITIVE -> new PrimitiveTypeHandler();
            case WRAPPER -> new WrapperTypeHandler();
            case USER_DEFINED -> new UserDefinedTypeHandler();
        };
    }
}
