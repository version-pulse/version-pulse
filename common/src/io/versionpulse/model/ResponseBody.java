package io.versionpulse.model;

public record ResponseBody(String json) {
	
    public static class Builder {
        private String json;

        public Builder type(String json) {
            this.json = json;
            return this;
        }

        public ResponseBody build() {
            return new ResponseBody(json);
        }
    }
}