package io.versionpulse.scanner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import io.versionpulse.annotation.ApiGroup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiGroupScanner {

	private final String packageName;

	public ApiGroupScanner(String packageName) {
		this.packageName = packageName;
	}

	public List<Class<?>> execute() {
		return scan(packageName);
	}

	private List<Class<?>> scan(String packageName) {
		List<Class<?>> result = new ArrayList<>();
		// 패키지 이름을 경로 형식으로 변환 ("com.example" -> "com/example")
		String packagePath = packageName.replace('.', '/');
		// 패키지의 URL 변환
		URL packageURL = ApiGroupScanner.class.getClassLoader().getResource(packagePath);

		if (packageURL == null) {
			log.error("Package not found: {}", packageName);
			return result;
		}

		try {
			Path path = Paths.get(packageURL.toURI());
			try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(path)) {
				// 디렉토리 내의 모든 파일을 처리
				for (Path entry : dirStream) {
					processEntry(entry, packageName, result);
				}
			}
		} catch (URISyntaxException | IOException | ClassNotFoundException e) {
			log.error("Error scanning package {}: {}", packageName, e.getMessage());
		}

		return result;
	}


	private void processEntry(Path entry, String packageName, List<Class<?>> result) throws ClassNotFoundException {
		// 디렉토리인 경우
		if (Files.isDirectory(entry)) {
			String subPackageName = packageName + "." + entry.getFileName();
			result.addAll(scan(subPackageName));
		}
		// 클래스 파일인 경우
		else if (entry.toString().endsWith(".class")) {
			String className = entry.getFileName().toString().substring(0, entry.getFileName().toString().length() - 6);
			Class<?> clazz = Class.forName(packageName + "." + className);
			if (clazz.isAnnotationPresent(ApiGroup.class)) {
				result.add(clazz);
			}
		}
	}
}
