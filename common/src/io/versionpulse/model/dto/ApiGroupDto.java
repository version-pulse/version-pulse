package io.versionpulse.model.dto;

import java.util.List;

public record ApiGroupDto(
        String groupTag,
        List<ApiDto> apis
) {

    public static class Builder {
        private String groupTag;
        private List<ApiDto> apis;

        public Builder groupTag(String groupTag) {
            this.groupTag = groupTag;
            return this;
        }

        public Builder apis(List<ApiDto> apis) {
            this.apis = apis;
            return this;
        }

        public ApiGroupDto build() {
            return new ApiGroupDto(groupTag, apis);
        }
    }
}