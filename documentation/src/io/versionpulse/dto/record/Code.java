package io.versionpulse.dto.record;

import java.util.List;

public record Code(String language, List<RichText> rich_text) {
	public static Code of(String language, String type, String content) {
		return new Code(language, List.of(new RichText(type, new Text(content))));
	}
}
