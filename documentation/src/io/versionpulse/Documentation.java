package io.versionpulse;
import java.util.List;

import io.versionpulse.client.CreateDatabaseClient;
import io.versionpulse.client.UpdateDatabaseClient;
import io.versionpulse.model.dto.ApiGroupDto;
import io.versionpulse.util.EnvManager;

public class Documentation {
	
	public void execute(String notionKey, String pageId, String databaseId, List<ApiGroupDto> records) {
		EnvManager envManager = new EnvManager();
		
		CreateDatabaseClient cClient = CreateDatabaseClient.builder()
				.notionKey(notionKey)
				.pageId(pageId)
				.build();
		
//		cClient.execute();
        
        UpdateDatabaseClient uClient = UpdateDatabaseClient.builder()
        		.notionKey(notionKey)
        		.databaseId(databaseId)
        		.records(records)
        		.build();
        uClient.execute();
	}
}
