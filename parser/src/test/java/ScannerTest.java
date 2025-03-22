package test.java;

import java.util.List;

import org.junit.Test;

import io.versionpulse.scanner.ApiGroupScanner;

public class ScannerTest {
	@Test
	public void test() {
		System.out.println("테스트");
		ApiGroupScanner groupScanner = new ApiGroupScanner("io.versionpulse");
		List<Class<?>> result = groupScanner.execute();
		for (Class<?> clazz : result) {
			System.out.println(clazz);
		}
	}
}