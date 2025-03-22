package io.versionpulse.apispecification;

import java.lang.reflect.Method;

import io.versionpulse.apispecification.inspector.HttpEndPointInspector;
import io.versionpulse.apispecification.model.HttpEndPointModel;


public class ApiSpecFetcher {
	private final Method method;
	private final String commonPath;
	
	public ApiSpecFetcher(Method method, String commonPath) {
		this.method = method;
		this.commonPath = commonPath;
	}
	
	public void fetch() {
		HttpEndPointModel endpoint = HttpEndPointInspector.execute(method);
		System.out.println(endpoint);
	}
}
