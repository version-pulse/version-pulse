package io.versionpulse.apispecification.inspector;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.versionpulse.apispecification.model.ResponseModel;
import io.versionpulse.model.ResponseBody;
import io.versionpulse.util.Converter;

public class ResponseInspector {

	public static ResponseModel execute(Method method) {
		Type genericReturnType = method.getGenericReturnType();
		try {
			String json;
			if (genericReturnType instanceof ParameterizedType parameterizedType) {
				Type typeArg = parameterizedType.getActualTypeArguments()[0];
				json = Converter.toJson(typeArg);
			} else {
				json = Converter.toJson(genericReturnType); // fallback
			}
			return new ResponseModel(new ResponseBody(json));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
