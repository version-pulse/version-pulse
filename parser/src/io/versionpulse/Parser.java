package io.versionpulse;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.versionpulse.apispecification.ApiSpecFetcher;
import io.versionpulse.model.dto.ApiDto;
import io.versionpulse.model.dto.ApiGroupDto;
import io.versionpulse.model.dto.ApiSchemaDto;
import io.versionpulse.scanner.AnnotationMetaDataScanner;
import io.versionpulse.scanner.ApiGroupScanner;
import io.versionpulse.scanner.ApiScanner;

public class Parser {
	public List<ApiGroupDto> execute(String packageName) {
		List<ApiGroupDto> finalDto = new ArrayList<>();
		ApiGroupScanner groupScanner = new ApiGroupScanner(packageName);
		
		List<Class<?>> classes = groupScanner.execute();
		for (Class<?> clazz : classes) {
			ApiGroupDto group = new ApiGroupDto(AnnotationMetaDataScanner.getApiGroupName(clazz), new ArrayList<>());
			ApiScanner apiScanner = new ApiScanner();
			
			List<Method> methods = apiScanner.execute(clazz);
			for (Method method : methods) {
				ApiSpecFetcher fetcher = new ApiSpecFetcher(method, AnnotationMetaDataScanner.getApiCommonPath(clazz));
				ApiSchemaDto apischema = fetcher.fetch();
				ApiDto api = new ApiDto(AnnotationMetaDataScanner.getApiName(method), AnnotationMetaDataScanner.getDetail(method), apischema);
				group.apis().add(api);
			}
			finalDto.add(group);
		}
		return finalDto;
	}
}
