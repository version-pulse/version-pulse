package io.versionpulse.apispecification;

import java.lang.reflect.Method;

import io.versionpulse.apispecification.inspector.HttpEndPointInspector;
import io.versionpulse.apispecification.inspector.RequestInspector;
import io.versionpulse.apispecification.inspector.ResponseInspector;
import io.versionpulse.apispecification.model.HttpEndPointModel;
import io.versionpulse.apispecification.model.RequestModel;
import io.versionpulse.apispecification.model.ResponseModel;
import io.versionpulse.model.dto.ApiSchemaDto;
import io.versionpulse.util.Formatter;


public class ApiSpecFetcher {
	private final Method method;
	private final String commonPath;
	
	public ApiSpecFetcher(Method method, String commonPath) {
		this.method = method;
		this.commonPath = commonPath;
	}
	
	public ApiSchemaDto fetch() {
		HttpEndPointModel endpointModel = HttpEndPointInspector.execute(method);
		if (endpointModel == null) return null;
		RequestModel requestModel = RequestInspector.execute(method);
		ResponseModel responseModel = ResponseInspector.execute(method);
		
		ApiSchemaDto apiSchema = new ApiSchemaDto.Builder()
				.method(endpointModel.httpMethod().name())
				.path(commonPath+endpointModel.path()+Formatter.toString(requestModel.queryString()))
				.queryString(requestModel.queryString())
				.parameter(requestModel.pathParameter())
				.requestBody(requestModel.requestBody())
				.responseBody(responseModel.responseBody())
				.build();
		
		return apiSchema;
	}
}
