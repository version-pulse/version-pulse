package io.versionpulse.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.versionpulse.util.handler.TypeHandler;
import io.versionpulse.util.handler.TypeHandlerFactory;

public class Converter {
	public static String toJson(Class<?> clazz) throws Exception {
		TypeHandler handler = TypeHandlerFactory.getHandler(clazz);
		return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(handler.handle(clazz));
	}

	public static String toJson(Class<?> clazz, String fieldName) throws Exception {
		if (TypeChecker.check(clazz) == ClassType.PRIMITIVE ||
				TypeChecker.check(clazz) == ClassType.WRAPPER) {
			return String.format("{\"%s\": \"%s\"}", fieldName, clazz.getSimpleName());
		}
		return toJson(clazz);
	}
}