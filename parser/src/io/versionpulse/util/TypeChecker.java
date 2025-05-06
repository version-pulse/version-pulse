package io.versionpulse.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

public class TypeChecker {

	public static ClassType check(Type type) {
		// 일반 클래스 타입
		if (type instanceof Class<?> clazz) {
			if (clazz.getPackage() == null || clazz.isPrimitive()) {
				return ClassType.PRIMITIVE;
			} else if (clazz.getPackage().getName().startsWith("java.lang")) {
				return ClassType.WRAPPER;
			} else if (Map.class.isAssignableFrom(clazz)) {
				return ClassType.MAP;
			} else if (Collection.class.isAssignableFrom(clazz)) {
				return ClassType.LIST;
			} else {
				return ClassType.USER_DEFINED;
			}
		}
		// 제네릭 타입
		else if (type instanceof ParameterizedType parameterizedType) {
			Type rawType = parameterizedType.getRawType();
			if (rawType instanceof Class<?> rawClazz) {
				return check(rawClazz);
			}
		}
		return ClassType.USER_DEFINED;
	}
}
