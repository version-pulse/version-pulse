package io.versionpulse.util;

import java.util.List;

import io.versionpulse.model.QueryString;

/**
 * @deprecated
 * @see io.versionpulse.model.QueryString
 * parser 모듈의 책임이 아님.
 * 키-값 쌍의 리스트까지만 담당하고, url을 만드는 것은 documentation 모듈의 책임
 */
@Deprecated
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
