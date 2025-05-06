package io.versionpulse.apispec.model;

import java.util.List;

import io.versionpulse.model.PathParameter;
import io.versionpulse.model.QueryString;
import io.versionpulse.model.RequestBody;


public record RequestModel(List<QueryString> queryString, List<PathParameter> pathParameter, RequestBody requestBody) {
	
}
