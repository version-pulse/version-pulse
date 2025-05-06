package io.versionpulse.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.versionpulse.common.ObjectMapperProvider;
import io.versionpulse.util.handler.TypeHandler;
import io.versionpulse.util.handler.TypeHandlerFactory;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;

@Slf4j
public class JsonConverter {
	private static final ObjectMapper objectMapper = ObjectMapperProvider.get();
	public static String toJson(Type type) {
		TypeHandler handler = TypeHandlerFactory.getHandler(type);

		try {
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(handler.handle(type));
		} catch (JsonProcessingException e) {
			log.error("json 변환 중 오류 발생 : "+e.getMessage());
			return "";
		}
	}
}