package io.versionpulse.dto.record;


public record Method(Select select) {
	public static Method of(String httpMethod) {
    	return new Method(new Select(httpMethod));
    }
}