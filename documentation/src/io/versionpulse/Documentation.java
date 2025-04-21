package io.versionpulse;
import java.util.List;

import io.versionpulse.client.CreateDatabaseClient;
import io.versionpulse.client.RetrieveDatabaseClient;
import io.versionpulse.client.UpdateDatabaseClient;
import io.versionpulse.model.dto.ApiGroupDto;
import io.versionpulse.util.EnvManager;

public class Documentation {

	public void execute(String notionKey, String pageId, String databaseId, List<ApiGroupDto> records) {
		if (isNullOrEmpty(databaseId)) {
			databaseId = retrieve(notionKey, pageId);
			if (isNullOrEmpty(databaseId)) {
				create(notionKey, pageId);
				databaseId = retrieve(notionKey, pageId);
			}
		}
		update(notionKey, databaseId, records);
	}

	private boolean isNullOrEmpty(String value) {
		return value == null || value.isEmpty();
	}

	private String retrieve(String notionKey, String pageId) {
		System.out.println("데이터베이스 검색");
		RetrieveDatabaseClient rClient = RetrieveDatabaseClient.builder()
				.notionKey(notionKey)
				.pageId(pageId)
				.build();
		return rClient.execute();
	}

	private void create(String notionKey, String pageId) {
		System.out.println("데이터베이스 생성");
		CreateDatabaseClient cClient = CreateDatabaseClient.builder()
				.notionKey(notionKey)
				.pageId(pageId)
				.build();
		cClient.execute();
	}

	private void update(String notionKey, String databaseId, List<ApiGroupDto> records) {
		System.out.println("데이터베이스 레코드 삽입");
		UpdateDatabaseClient uClient = UpdateDatabaseClient.builder()
				.notionKey(notionKey)
				.databaseId(databaseId)
				.records(records)
				.build();
		uClient.execute();
	}
}
