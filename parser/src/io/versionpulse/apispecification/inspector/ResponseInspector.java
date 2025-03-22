package io.versionpulse.apispecification.inspector;

import java.lang.reflect.Method;

import io.versionpulse.apispecification.model.ResponseModel;
import io.versionpulse.model.ResponseBody;
import io.versionpulse.util.Converter;

public class ResponseInspector {
	
	public static ResponseModel execute(Method method) {
		Class<?> clazz = method.getReturnType();
		
		try {
			String json = Converter.toJson(clazz);
	        return new ResponseModel(new ResponseBody(json));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
