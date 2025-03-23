package test.java;


import org.junit.Test;

import io.versionpulse.client.CreateDatabaseClient;
import io.versionpulse.client.UpdateDatabaseClient;
import io.versionpulse.util.EnvManager;

public class ControllerTest {
	EnvManager envManager = new EnvManager();
	@Test
	public void test() {
		CreateDatabaseClient cClient = CreateDatabaseClient.builder()
				.notionKey(envManager.getValueByKey("NOTION_KEY"))
				.pageId(envManager.getValueByKey("PAGE_ID"))
				.build();
		
//		cClient.execute();     
        
        UpdateDatabaseClient uClient = UpdateDatabaseClient.builder()
        		.notionKey(envManager.getValueByKey("NOTION_KEY"))
        		.databaseId(envManager.getValueByKey("DATABASE_ID"))
        		.build();
        uClient.execute();
	}
}
