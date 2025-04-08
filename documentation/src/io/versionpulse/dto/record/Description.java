package io.versionpulse.dto.record;

import java.util.List;

public record Description(List<RichText> rich_text) {
	public static Description of(String text) {
		return new Description(List.of(new RichText(new Text(text))));
	}
}
