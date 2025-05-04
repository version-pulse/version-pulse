package io.versionpulse.apispecification.inspector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import io.versionpulse.apispecification.model.HttpEndPointModel;

public class HttpEndPointInspector {

	public static HttpEndPointModel execute(Method method) {
		Annotation[] annotations = method.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof GetMapping getMapping) {
                return new HttpEndPointModel(HttpMethod.GET, getMapping.value()[0]);
	        }
			else if (annotation instanceof PostMapping postMapping) {
                return new HttpEndPointModel(HttpMethod.POST, postMapping.value()[0]);
	        }
			else if (annotation instanceof PutMapping putMapping) {
                return new HttpEndPointModel(HttpMethod.PUT, putMapping.value()[0]);
	        }
			else if (annotation instanceof DeleteMapping deleteMapping) {
                return new HttpEndPointModel(HttpMethod.DELETE, deleteMapping.value()[0]);
	        }
			else if (annotation instanceof PatchMapping patchMapping) {
                return new HttpEndPointModel(HttpMethod.PATCH, patchMapping.value()[0]);
	        }
		}
        return null;
	}
}
