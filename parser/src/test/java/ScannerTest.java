package test.java;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;

import io.versionpulse.scanner.ApiGroupScanner;
import io.versionpulse.scanner.ApiScanner;

public class ScannerTest {
	@Test
	public void test() {
		ApiGroupScanner groupScanner = new ApiGroupScanner("io.versionpulse");
		List<Class<?>> classes = groupScanner.execute();
		for (Class<?> clazz : classes) {
			System.out.println(clazz);
			ApiScanner apiScanner = new ApiScanner();
			List<Method> methods = apiScanner.execute(clazz);
			for (Method method : methods) {
				System.out.println(method);
			}
		}
	}
}