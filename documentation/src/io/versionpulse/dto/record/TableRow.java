package io.versionpulse.dto.record;

import java.util.List;

import lombok.Builder;

@Builder
public record TableRow(List<List<Cell>> cells) {
	public static TableRow of(List<String> contents) {
        List<List<Cell>> cells = contents.stream()
            .map(content -> List.of(Cell.of(content)))
            .toList();
        return new TableRow(cells);
    }
}
