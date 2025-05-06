package io.versionpulse.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.versionpulse.annotation.Api;


public class ApiMethodScanner {
	
	public List<Method> execute(Class<?> clazz) {
		List<Method> result = new ArrayList<>();
		
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			if (isTargetMethod(method)) {
				result.add(method);
			}
		}
		return result;
	}
	
	// Api 어노테이션이 있는 메소드만 포함
	private boolean isTargetMethod(Method method) {
		Annotation[] annotations = method.getDeclaredAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof Api) {
				return true;
			}
		}
		return false;
	}
}