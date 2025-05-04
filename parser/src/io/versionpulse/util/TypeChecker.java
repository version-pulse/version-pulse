package io.versionpulse.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static io.versionpulse.util.ClassType.*;

public class TypeChecker {

	public static ClassType check(Type type) {
		if (type instanceof Class<?> clazz) {
			if (clazz.getPackage() == null || clazz.isPrimitive()) {
				return ClassType.PRIMITIVE;
			} else if (clazz.getPackage().getName().startsWith("java.lang")) {
				return ClassType.WRAPPER;
			} else if (clazz.getName().startsWith("java.util.Map")) {
				return ClassType.MAP;
			} else if (clazz.getName().startsWith("java.util")) {
				return ClassType.LIST;
			} else {
				return ClassType.USER_DEFINED;
			}
		} else if (type instanceof ParameterizedType parameterizedType) {
			Type rawType = parameterizedType.getRawType();
			if (rawType instanceof Class<?> rawClazz) {
				return check(rawClazz);
			}
		}
		return ClassType.USER_DEFINED;
	}

}
