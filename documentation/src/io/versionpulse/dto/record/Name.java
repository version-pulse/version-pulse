package io.versionpulse.dto.record;

import java.util.List;

public record Name(List<Title> title) {
	public static Name of (String text) {
		return new Name(List.of(new Title(new Text(text))));
	}
}
