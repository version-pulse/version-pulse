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
			String json = null;
			if (genericReturnType instanceof ParameterizedType) {
				ParameterizedType type = (ParameterizedType) genericReturnType;
				Type[] typeArguments = type.getActualTypeArguments();

				for (Type t : typeArguments) {
					json = Converter.toJson(t);
//					if (t instanceof Class<?> clazz) {
//						json = Converter.toJson(t);
//					} else if (t instanceof ParameterizedType innerType) {
//						Type rawType = innerType.getRawType();
//						if (rawType instanceof Class<?> innerClazz) {
//							json = Converter.toJson(innerClazz);
//						}
//					} else {
//						json = Converter.toJson(method.getReturnType());
//					}
				}
			}

	        return new ResponseModel(new ResponseBody(json));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
