package io.versionpulse.util;

import io.versionpulse.dto.record.Child;
import io.versionpulse.dto.record.Heading2;

public class HeadingFormatter {
	public static Child toHeading(String title) {
		Heading2 heading = Heading2.of("text", title);
    	return Child.of("block", "heading_2", heading);
	}
}
