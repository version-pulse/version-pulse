package io.versionpulse.apispecification;

import java.lang.reflect.Method;

import io.versionpulse.apispecification.inspector.HttpEndPointInspector;
import io.versionpulse.apispecification.inspector.RequestInspector;
import io.versionpulse.apispecification.inspector.ResponseInspector;
import io.versionpulse.apispecification.model.HttpEndPointModel;
import io.versionpulse.apispecification.model.RequestModel;
import io.versionpulse.apispecification.model.ResponseModel;


public class ApiSpecFetcher {
	private final Method method;
	private final String commonPath;
	
	public ApiSpecFetcher(Method method, String commonPath) {
		this.method = method;
		this.commonPath = commonPath;
	}
	
	public void fetch() {
		HttpEndPointModel endpointModel = HttpEndPointInspector.execute(method);
		if (endpointModel == null) return;
		System.out.println(endpointModel);
		RequestModel requestModel = RequestInspector.execute(method);
		System.out.println(requestModel);
		ResponseModel responseModel = ResponseInspector.execute(method);
		System.out.println(responseModel);
	}
}
