package io.versionpulse.dto.record;

import java.util.List;

public record Paragraph(List<RichText> rich_text) {
	public static Paragraph of(String text) {
    	return new Paragraph(List.of(new RichText(new Text(text))));
    }
}
