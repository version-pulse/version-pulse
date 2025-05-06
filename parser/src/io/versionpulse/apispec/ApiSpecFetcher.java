package io.versionpulse.apispec;

import java.lang.reflect.Method;

import io.versionpulse.apispec.inspector.HttpEndPointInspector;
import io.versionpulse.apispec.inspector.RequestInspector;
import io.versionpulse.apispec.inspector.ResponseInspector;
import io.versionpulse.apispec.model.HttpEndPointModel;
import io.versionpulse.apispec.model.RequestModel;
import io.versionpulse.apispec.model.ResponseModel;
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
