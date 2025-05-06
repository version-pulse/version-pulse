package io.versionpulse.scanner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.versionpulse.annotation.Api;


public class ApiMethodScanner {
	
	public List<Method> execute(Class<?> clazz) {
		List<Method> result = new ArrayList<>();
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(Api.class)) {
				result.add(method);
			}
		}
		return result;
	}
}