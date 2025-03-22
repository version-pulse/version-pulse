package io.versionpulse.model.dto;

import java.util.List;

import io.versionpulse.model.PathParameter;
import io.versionpulse.model.QueryString;
import io.versionpulse.model.RequestBody;
import io.versionpulse.model.ResponseBody;

public record ApiSchemaDto(
        String method,
        String path,
        List<QueryString> queryString,
        List<PathParameter> parameter,
        RequestBody requestBody,
        ResponseBody responseBody
) {

    public static class Builder {
        private String method;
        private String path;
        private List<QueryString> queryString;
        private List<PathParameter> parameter;
        private RequestBody requestBody;
        private ResponseBody responseBody;

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder queryString(List<QueryString> queryString) {
            this.queryString = queryString;
            return this;
        }

        public Builder parameter(List<PathParameter> parameter) {
            this.parameter = parameter;
            return this;
        }

        public Builder requestBody(RequestBody requestBody) {
            this.requestBody = requestBody;
            return this;
        }

        public Builder responseBody(ResponseBody responseBody) {
            this.responseBody = responseBody;
            return this;
        }

        public ApiSchemaDto build() {
            return new ApiSchemaDto(method, path, queryString, parameter, requestBody, responseBody);
        }
    }
}
