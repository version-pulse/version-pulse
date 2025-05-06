package io.versionpulse;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.versionpulse.apispec.ApiSpecFetcher;
import io.versionpulse.model.dto.ApiDto;
import io.versionpulse.model.dto.ApiGroupDto;
import io.versionpulse.model.dto.ApiSchemaDto;
import io.versionpulse.scanner.AnnotationPropertyScanner;
import io.versionpulse.scanner.ApiGroupScanner;
import io.versionpulse.scanner.ApiMethodScanner;

public class Parser {
	public List<ApiGroupDto> execute(String packageName) {
		List<ApiGroupDto> finalDto = new ArrayList<>();
		ApiGroupScanner groupScanner = new ApiGroupScanner(packageName);
		
		List<Class<?>> classes = groupScanner.execute();
		for (Class<?> clazz : classes) {
			ApiGroupDto group = new ApiGroupDto(AnnotationPropertyScanner.getApiGroupName(clazz), new ArrayList<>());
			ApiMethodScanner apiMethodScanner = new ApiMethodScanner();
			
			List<Method> methods = apiMethodScanner.execute(clazz);
			for (Method method : methods) {
				ApiSpecFetcher fetcher = new ApiSpecFetcher(method, AnnotationPropertyScanner.getApiCommonPath(clazz));
				ApiSchemaDto apischema = fetcher.fetch();
				ApiDto api = new ApiDto(AnnotationPropertyScanner.getApiName(method), AnnotationPropertyScanner.getApiDetail(method), apischema);
				group.apis().add(api);
			}
			finalDto.add(group);
		}
		return finalDto;
	}
}
