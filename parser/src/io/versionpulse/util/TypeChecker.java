package io.versionpulse.util;

import static io.versionpulse.util.ClassType.*;

public class TypeChecker {
	
	public static ClassType check(Class<?> clazz) {
		if (clazz.getPackage() == null || clazz.isPrimitive()) {
			return PRIMITIVE;
		}
		else if (clazz.getPackage().getName().startsWith("java.lang")) {
			return WRAPPER;
		}
		else {
			return USER_DEFINED;
		}
	}
}
