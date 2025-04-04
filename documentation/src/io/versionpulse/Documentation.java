package io.versionpulse;
import io.versionpulse.client.CreateDatabaseClient;
import io.versionpulse.client.UpdateDatabaseClient;
import io.versionpulse.util.EnvManager;

public class Documentation {
	
	public void execute(String notionKey, String pageId, String databaseId) {
		EnvManager envManager = new EnvManager();
		
		CreateDatabaseClient cClient = CreateDatabaseClient.builder()
				.notionKey(notionKey)
				.pageId(pageId)
				.build();
		
//		cClient.execute();
        
        UpdateDatabaseClient uClient = UpdateDatabaseClient.builder()
        		.notionKey(notionKey)
        		.databaseId(databaseId)
        		.build();
        uClient.execute();
	}
}
