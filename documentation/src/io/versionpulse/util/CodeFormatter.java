package io.versionpulse.util;

import io.versionpulse.dto.record.Child;
import io.versionpulse.dto.record.Code;

public class CodeFormatter {
	public static Child toCode(String json) {
		Code code = Code.of("json", "text", json);
		return Child.of("block", "code", code);
	}
}
