package io.versionpulse.dto.record;

import lombok.Builder;

@Builder
public record Cell(
		String type,
		Text text,
		Annotations annotations,
		String plain_text,
		String href) 
{
	public static Cell of(String text) {
        return new Cell(
            "text",
            new Text(text),
            new Annotations(false, false, false, false, false, "default"),
            text,
            null
        );
    }
}