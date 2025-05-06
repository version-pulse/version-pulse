package io.versionpulse.scanner;

import java.lang.reflect.Method;

import org.springframework.web.bind.annotation.RestController;

import io.versionpulse.annotation.Api;
import io.versionpulse.annotation.ApiGroup;


public class AnnotationPropertyScanner {
	
	public static String getApiGroupName(Class<?> clazz) {
		ApiGroup apiGroup = clazz.getAnnotation(ApiGroup.class);
        return apiGroup.name();
	}
	
	public static String getApiCommonPath(Class<?> clazz) {
		RestController restController = clazz.getAnnotation(RestController.class);
		return restController.value();
	}
	
	public static String getApiDetail(Method method) {
		Api api = method.getAnnotation(Api.class);
		return api.detail();
	}
	
	public static String getApiName(Method method) {
		Api api = method.getAnnotation(Api.class);
        return api.name();
	}
}
