package test.java;


import org.junit.Test;

import io.versionpulse.client.CreateDatabaseClient;
import io.versionpulse.util.EnvManager;

public class ControllerTest {
	EnvManager envManager = new EnvManager();
	@Test
	public void test() {
		CreateDatabaseClient client = CreateDatabaseClient.builder()
				.notionKey(envManager.getValueByKey("NOTION_KEY"))
				.pageId(envManager.getValueByKey("PAGE_ID"))
				.build();
		
        client.execute();        
	}
}
