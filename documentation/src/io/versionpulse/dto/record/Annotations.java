package io.versionpulse.dto.record;

public record Annotations(
		boolean bold,
        boolean italic,
        boolean strikethrough,
        boolean underline,
        boolean code,
        String color
        ) {

}
