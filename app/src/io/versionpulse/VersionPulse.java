package io.versionpulse;

import java.util.List;

import io.versionpulse.model.dto.ApiGroupDto;
import io.versionpulse.util.EnvManager;

public class VersionPulse {
	private final String packageName;
	private final String notionKey;
	private final String pageId;
	private final String databaseId;
	
	private VersionPulse(Builder builder) {
        this.packageName = builder.packageName;
        this.notionKey = builder.notionKey;
        this.pageId = builder.pageId;
        this.databaseId = builder.databaseId;
        execute();
    }
	
	public void execute() {
		Parser parser = new Parser();
		List<ApiGroupDto> data = parser.execute(packageName);
		System.out.println(data);
		
		Documentation documentation = new Documentation();
		documentation.execute(notionKey, pageId, databaseId);
	}
	
	public static Builder builder() {
        return new Builder();
    }
	
	public static class Builder {
        private String packageName;
        private String notionKey;
        private String pageId;
        private String databaseId;

        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder notionKey(String notionKey) {
            this.notionKey = notionKey;
            return this;
        }

        public Builder pageId(String pageId) {
            this.pageId = pageId;
            return this;
        }

        public Builder databaseId(String databaseId) {
            this.databaseId = databaseId;
            return this;
        }

        public VersionPulse build() {
            return new VersionPulse(this);
        }
    }
}
