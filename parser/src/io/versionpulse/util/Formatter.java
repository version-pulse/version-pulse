package io.versionpulse.util;

import java.util.List;

import io.versionpulse.model.HttpParameter;
import io.versionpulse.model.PathParameter;
import io.versionpulse.model.QueryString;
import io.versionpulse.model.RequestBody;
import io.versionpulse.model.ResponseBody;

public class Formatter {
	
	public static String toString(List<QueryString> list) {
	    if (list == null || list.isEmpty()) {
	        return "";
	    }

	    StringBuilder sb = new StringBuilder();
        sb.append("?");
        for (QueryString item : list) {
            sb.append(item.name()).append("=").append(item.type()).append("&");
        }
        sb.deleteCharAt(sb.length() - 1); // 마지막 '&' 제거
	    return sb.toString();
	}
}
