package io.versionpulse.scanner;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import io.versionpulse.annotation.ApiGroup;

public class ApiGroupScanner {
	
	private String packageName;
	
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

	    try {
	        Path path = Paths.get(packageURL.toURI());
	        
	        if (!Files.isDirectory(path)) {
	            return null;
	        }
	        
	        DirectoryStream<Path> dirStream = Files.newDirectoryStream(path);
	        // 디렉터리 내의 모든 파일을 처리
	        for (Path entry : dirStream) {
	            processEntry(entry, packageName, result);
	        }
	    } catch (URISyntaxException | IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    
	    return result;
	}

	
	private void processEntry(Path entry, String packageName, List<Class<?>> result) throws ClassNotFoundException {
		// 하위 디렉터리가 있다면 재귀적으로 탐색
	    if (Files.isDirectory(entry)) {
	    	String subPackageName = packageName + "." + entry.getFileName();
		    result.addAll(scan(subPackageName));
	    } 
	    // .class 파일이라면 클래스 로딩
	    else if (entry.toString().endsWith(".class")) {
	    	String className = entry.getFileName().toString().substring(0, entry.getFileName().toString().length() - 6);
		    Class<?> clazz = Class.forName(packageName + "." + className);
		    if (isTargetClass(clazz)) {
		        result.add(clazz);
		    }
	    }
	}


	// ApiGroup 어노테이션이 있는 클래스만 포함
	private boolean isTargetClass(Class<?> clazz) {
		Class<? extends Annotation> annotationClass = ApiGroup.class;
		return clazz.isAnnotationPresent(annotationClass);
	}
}

