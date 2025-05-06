package io.versionpulse.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

public class TypeChecker {

	public static TypeCategory check(Type type) {
		// 일반 클래스 타입
		if (type instanceof Class<?> clazz) {
			if (clazz.getPackage() == null || clazz.isPrimitive()) {
				return TypeCategory.PRIMITIVE;
			} else if (clazz.getPackage().getName().startsWith("java.lang")) {
				return TypeCategory.WRAPPER;
			} else if (Map.class.isAssignableFrom(clazz)) {
				return TypeCategory.MAP;
			} else if (Collection.class.isAssignableFrom(clazz)) {
				return TypeCategory.LIST;
			} else {
				return TypeCategory.USER_DEFINED;
			}
		}
		// 제네릭 타입
		else if (type instanceof ParameterizedType parameterizedType) {
			Type rawType = parameterizedType.getRawType();
			if (rawType instanceof Class<?> rawClazz) {
				return check(rawClazz);
			}
		}
		return TypeCategory.USER_DEFINED;
	}
}
