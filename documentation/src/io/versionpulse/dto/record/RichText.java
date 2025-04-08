package io.versionpulse.dto.record;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * type : code 일때 언어 설정
 */
public record RichText(@JsonInclude(value = Include.NON_NULL) String type, Text text) {

	public RichText(Text text) {
		this(null, text);
	}
}
