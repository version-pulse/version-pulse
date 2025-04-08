package io.versionpulse.util;

import java.util.List;

import io.versionpulse.model.HttpParameter;

public class TableFormatter {
	public static <T extends HttpParameter> String toTable(List<T> list) {
		StringBuffer sb = new StringBuffer();
		for (HttpParameter parameter : list) {
			sb.append(parameter.name()).append(" ").append(parameter.type()).append("\n");
		}
		return sb.toString();
	}
}
