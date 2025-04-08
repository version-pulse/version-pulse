package io.versionpulse.util;

import java.util.ArrayList;
import java.util.List;

import io.versionpulse.dto.record.Child;
import io.versionpulse.dto.record.Table;
import io.versionpulse.dto.record.TableRow;
import io.versionpulse.model.HttpParameter;

public class TableFormatter {
	public static <T extends HttpParameter> Child toTable(List<T> params) {
		List<Child> children = new ArrayList<>();
		children.add(makeHeader());
		for (HttpParameter param : params) {
			children.add(makeRow(param));
		}
		
		Table table = Table.builder()
    		    .table_width(2)
    		    .has_column_header(true)
    		    .has_row_header(false)
    		    .children(children)
    		    .build();
		return Child.of("block", "table", table);
	}
	
	private static Child makeHeader() {
		TableRow header = TableRow.of(List.of("변수명", "자료형"));
		return Child.builder()
    		    .object("block")
    		    .type("table_row")
    		    .table_row(header)
    		    .build();
	}
	
	private static Child makeRow(HttpParameter param) {
		TableRow header = TableRow.of(List.of(param.name(), param.type()));
		return Child.builder()
    		    .object("block")
    		    .type("table_row")
    		    .table_row(header)
    		    .build();
	}
}
