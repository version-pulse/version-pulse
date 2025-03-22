package main.java.model.dto;

public record ApiDto(
        String name,
        String description,
        ApiSchemaDto apiSchemaDto
) {

    // Builder 클래스 정의
    public static class Builder {
        private String name;
        private String description;
        private ApiSchemaDto apiSchemaDto;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder apiSchemaDto(ApiSchemaDto apiSchemaDto) {
            this.apiSchemaDto = apiSchemaDto;
            return this;
        }

        public ApiDto build() {
            return new ApiDto(name, description, apiSchemaDto);
        }
    }
}
