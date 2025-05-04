package io.versionpulse.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.versionpulse.util.handler.TypeHandler;
import io.versionpulse.util.handler.TypeHandlerFactory;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;

@Slf4j
public class Converter {
	public static String toJson(Type type) {
		TypeHandler handler = TypeHandlerFactory.getHandler(type);

		try {
			return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(handler.handle(type));
		} catch (JsonProcessingException e) {
			log.error("json 변환 중 오류 발생 : "+e.getMessage());
			return "";
		}
	}

	public static String toJson(Type type, String fieldName) {
		ClassType classType = TypeChecker.check(type);
		if (classType == ClassType.PRIMITIVE || classType == ClassType.WRAPPER) {
			String typeName = type.getTypeName();
			return String.format("{\"%s\": \"%s\"}", fieldName, typeName.substring(typeName.lastIndexOf('.') + 1));
		}
		return toJson(type);
	}
}