package io.versionpulse.dto.record;

import java.util.List;

import lombok.Builder;

@Builder
public record Table(
		int table_width,
        boolean has_column_header,
        boolean has_row_header,
        List<Child> children) 
{
	
}
