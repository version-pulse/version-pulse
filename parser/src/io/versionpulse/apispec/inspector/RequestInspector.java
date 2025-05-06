package io.versionpulse.apispec.inspector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import io.versionpulse.apispec.model.RequestModel;
import io.versionpulse.model.PathParameter;
import io.versionpulse.model.QueryString;
import io.versionpulse.model.RequestBody;
import io.versionpulse.util.Converter;

public class RequestInspector {
	
	public static RequestModel execute(Method method) {
		List<QueryString> queryStringList = new ArrayList<>();
		List<PathParameter> pathParameterList = new ArrayList<>();
		RequestBody requestbody = new RequestBody();
		
		Parameter[] parameters = method.getParameters();
		for (Parameter parameter : parameters) {
			Annotation[] annotations = parameter.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation instanceof RequestParam requestParam) {
					queryStringList.add(getQueryString(parameter, requestParam));
				}
				else if (annotation instanceof PathVariable pathVariable) {
					pathParameterList.add(getPathParameter(parameter, pathVariable));
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
		Type type = parameter.getParameterizedType();
		return new RequestBody(Converter.toJson(type, parameter.getName()));
	}
}
