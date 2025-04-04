package io.versionpulse.model;

public record PathParameter(String type, String name) implements HttpParameter {
	
    public static class Builder {
        private String type;
        private String name;

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public PathParameter build() {
            return new PathParameter(type, name);
        }
    }

    @Override
    public String toString() {
        return String.format("%s=%s", name, type);
    }
}