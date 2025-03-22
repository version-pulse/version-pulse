package main.java.model;

public record RequestBody(String json) {
	
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