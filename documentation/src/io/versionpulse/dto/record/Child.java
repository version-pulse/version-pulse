package io.versionpulse.dto.record;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;

@Builder
public record Child(
        String object,
        String type,
        @JsonInclude(value = Include.NON_NULL) Heading2 heading_2,
        @JsonInclude(value = Include.NON_NULL) Paragraph paragraph,
        @JsonInclude(value = Include.NON_NULL) Code code,
        @JsonInclude(value = Include.NON_NULL) Table table,
        @JsonInclude(value = Include.NON_NULL) TableRow table_row
    ) {
	public static Child of(String object, String type, Object content) {
    	if (content instanceof Heading2) {
    		return Child.builder()
        			.object(object)
        			.type(type)
        			.heading_2((Heading2) content)
        			.build();
    	}
    	else if (content instanceof Paragraph) {
    		return Child.builder()
        			.object(object)
        			.type(type)
        			.paragraph((Paragraph) content)
        			.build();
    	}
    	else if (content instanceof Code) {
    		return Child.builder()
    				.object(object)
    				.type(type)
    				.code((Code) content)
    				.build();
    	}
    	else if (content instanceof Table) {
    		return Child.builder()
    				.object(object)
    				.type(type)
    				.table((Table) content)
    				.build();
    	}
    	return null;
    }
}