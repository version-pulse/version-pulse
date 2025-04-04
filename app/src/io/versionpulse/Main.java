package io.versionpulse;

import io.versionpulse.util.EnvManager;

public class Main {
	private static EnvManager envManager = new EnvManager();
	
	public static void main(String[] args) {
		VersionPulse.builder()
		.notionKey(envManager.getValueByKey("NOTION_KEY"))
		.databaseId(envManager.getValueByKey("DATABASE_ID"))
		.pageId(envManager.getValueByKey("PAGE_ID"))
		.packageName(envManager.getValueByKey("PACKAGE_NAME"))
		.build();
	}
}