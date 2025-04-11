package test.java;

import org.junit.Test;

import io.versionpulse.VersionPulse;
import io.versionpulse.util.EnvManager;

public class AppTest {
	@Test
	public void test() {
		EnvManager envManager = new EnvManager();
		
		VersionPulse.builder()
		.notionKey(envManager.getValueByKey("NOTION_KEY"))
		.databaseId(envManager.getValueByKey("DATABASE_ID"))
		.pageId(envManager.getValueByKey("PAGE_ID"))
		.packageName(envManager.getValueByKey("PACKAGE_NAME"))
		.build();
	}
}
