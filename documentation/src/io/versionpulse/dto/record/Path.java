package io.versionpulse.dto.record;

import java.util.List;

public record Path(List<RichText> rich_text) {
	public static Path of(String text) {
		return new Path(List.of(new RichText(new Text(text))));
	}
}
