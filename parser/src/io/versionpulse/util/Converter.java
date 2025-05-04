package io.versionpulse.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.versionpulse.util.handler.TypeHandler;
import io.versionpulse.util.handler.TypeHandlerFactory;

import java.lang.reflect.Type;

public class Converter {
	public static String toJson(Type type) throws Exception {
		TypeHandler handler = TypeHandlerFactory.getHandler(type);
		return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(handler.handle(type));
	}

	public static String toJson(Type type, String fieldName) throws Exception {
		if (TypeChecker.check(type) == ClassType.PRIMITIVE ||
				TypeChecker.check(type) == ClassType.WRAPPER) {
			String typeName = type.getTypeName();
			return String.format("{\"%s\": \"%s\"}", fieldName, typeName.substring(typeName.lastIndexOf('.') + 1));
		}
		return toJson(type);
	}
}