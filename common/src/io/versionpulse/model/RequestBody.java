package io.versionpulse.model;

public record RequestBody(String json) {
	
    public RequestBody() {
		this("");
	}

	public static class Builder {
        private String json;

        public Builder type(String json) {
            this.json = json;
            return this;
        }

        public RequestBody build() {
            return new RequestBody(json);
        }
    }
}