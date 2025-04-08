package io.versionpulse.dto.record;

import lombok.Builder;

@Builder
public record Properties(
	Name name,
    Description description,
    Path path,
    Check check,
    Method method
) { }