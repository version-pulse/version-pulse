package io.versionpulse.apispec.inspector;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.versionpulse.apispec.model.ResponseModel;
import io.versionpulse.model.ResponseBody;
import io.versionpulse.util.JsonConverter;

public class ResponseInspector {

	public static ResponseModel execute(Method method) {
		Type genericReturnType = method.getGenericReturnType();
		String json = "";

		if (genericReturnType instanceof ParameterizedType parameterizedType) {
			Type rawType = parameterizedType.getRawType();
			if (rawType instanceof Class<?> rawClass && rawClass.getSimpleName().equals("ResponseEntity")) {
				Type[] typeArgs = parameterizedType.getActualTypeArguments();
				Type actualType = typeArgs[0];
				json = JsonConverter.toJson(actualType);
				return new ResponseModel(new ResponseBody(json));
			}
		} else {
			json = JsonConverter.toJson(genericReturnType);
		}
		return new ResponseModel(new ResponseBody(json));
	}
}
