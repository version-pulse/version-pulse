package io.versionpulse.util.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.versionpulse.common.ObjectMapperProvider;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class UserDefinedTypeHandler implements TypeHandler {
    private static final ObjectMapper objectMapper = ObjectMapperProvider.get();
    private static final UserDefinedTypeHandler INSTANCE = new UserDefinedTypeHandler();

    private UserDefinedTypeHandler() {}

    public static UserDefinedTypeHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public JsonNode handle(Type type) {
        ObjectNode node = objectMapper.createObjectNode();

        Class<?> clazz = getClassFromType(type);

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true); // 필드 접근을 허용
            String name = field.getName();
            Type fieldType = field.getGenericType();

            // 필드 타입에 맞는 핸들러를 가져와 실행
            TypeHandler handler = TypeHandlerFactory.getHandler(fieldType);
            JsonNode fieldNode = handler.handle(fieldType);
            node.set(name, fieldNode);
        }
        return node;
    }

    private Class<?> getClassFromType(Type type) {
        // 일반 클래스 타입
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        }
        // 제네릭 타입
        else if (type instanceof ParameterizedType parameterizedType) {
            return (Class<?>) parameterizedType.getRawType();
        }
        else {
            throw new IllegalArgumentException("Unsupported Type: " + type.getTypeName());
        }
    }
}
