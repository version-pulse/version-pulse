package main.java.model;

public record QueryString(String type, String name) {

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

        public QueryString build() {
            return new QueryString(type, name);
        }
    }

    @Override
    public String toString() {
        return String.format("%s=%s", name, type);
    }
}