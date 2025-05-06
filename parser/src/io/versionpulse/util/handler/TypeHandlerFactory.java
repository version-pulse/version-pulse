package io.versionpulse.util.handler;

import io.versionpulse.util.TypeCategory;
import io.versionpulse.util.TypeChecker;

import java.lang.reflect.Type;

public class TypeHandlerFactory {

    public static TypeHandler getHandler(Type type) {
        TypeCategory category = TypeChecker.check(type);
        return switch (category) {
            case PRIMITIVE -> PrimitiveTypeHandler.getInstance();
            case WRAPPER -> WrapperTypeHandler.getInstance();
            case LIST -> ListTypeHandler.getInstance();
            case MAP -> MapTypeHandler.getInstance();
            case USER_DEFINED -> UserDefinedTypeHandler.getInstance();
        };
    }
}
