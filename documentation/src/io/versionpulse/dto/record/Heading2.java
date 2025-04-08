package io.versionpulse.dto.record;

import java.util.List;

public record Heading2(List<RichText> rich_text) {
	public static Heading2 of(String type, String content) {
    	return new Heading2(List.of(new RichText(type, new Text(content))));
    }
}
