package io.versionpulse.apispecification.inspector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import io.versionpulse.apispecification.model.RequestModel;
import io.versionpulse.model.PathParameter;
import io.versionpulse.model.QueryString;
import io.versionpulse.model.RequestBody;
import io.versionpulse.util.Converter;

public class RequestInspector {
	
	public static RequestModel execute(Method method) {
		List<QueryString> queryStringList = new ArrayList<>();
		List<PathParameter> pathParameterList = new ArrayList<>();
		RequestBody requestbody = null;
		
		Parameter[] parameters = method.getParameters();
		for (Parameter parameter : parameters) {
			Annotation[] annotations = parameter.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation instanceof RequestParam) {
					queryStringList.add(getQueryString(parameter, (RequestParam) annotation));
				}
				else if (annotation instanceof PathVariable) {
					pathParameterList.add(getPathParameter(parameter, (PathVariable) annotation));
				}
				else if (annotation instanceof org.springframework.web.bind.annotation.RequestBody) {
					requestbody = getRequestBody(parameter);
				}
			}
		}
		return new RequestModel(queryStringList, pathParameterList, requestbody);
	}
	
	
	private static QueryString getQueryString(Parameter parameter, RequestParam requestParam) {
		return new QueryString(parameter.getType().getSimpleName(), requestParam.name());
	}
	
	
	private static PathParameter getPathParameter(Parameter parameter, PathVariable pathVariable) {
		return new PathParameter(parameter.getType().getSimpleName(), pathVariable.name());
	}
	
	
	private static RequestBody getRequestBody(Parameter parameter) {
		Type type = parameter.getType();
		Class<?> clazz = (type instanceof Class<?>) ? (Class<?>) type : null;
		
		try {
			return new RequestBody(Converter.toJson(clazz, parameter.getName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
