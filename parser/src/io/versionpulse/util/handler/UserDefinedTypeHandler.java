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

    @Override
    public JsonNode handle(Type type) {
        Class<?> clazz;

        // Type이 Class<?> 인스턴스일 경우
        if (type instanceof Class<?>) {
            clazz = (Class<?>) type;
        }
        // Type이 ParameterizedType인 경우
        else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            clazz = (Class<?>) parameterizedType.getRawType();
        } else {
            throw new IllegalArgumentException("Unsupported Type: " + type.getTypeName());
        }

        ObjectNode node = objectMapper.createObjectNode();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);  // 필드 접근을 허용
            String name = field.getName();
            Class<?> fieldType = field.getType();
            Type fieldGenericType = field.getGenericType();  // 제네릭 타입을 가져옴

            // TypeHandler를 이용하여 필드 타입에 맞는 핸들러를 가져옴
            TypeHandler handler = TypeHandlerFactory.getHandler(fieldType);

            node.set(name, handler.handle(fieldGenericType));
        }

        return node;
    }
}
