package io.versionpulse.util;

import java.lang.reflect.Field;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Converter {
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	
	public static String toJson(Class<?> clazz, String type) throws Exception {
		ClassType classType = TypeChecker.check(clazz);
		String result = null;
		
		switch (classType) {
		    case PRIMITIVE:
		    	result = fieldToJson(type, clazz.getName());
		    	break;
		    case WRAPPER:
		        result = fieldToJson(type, clazz.getSimpleName());
		        break;
		    case USER_DEFINED:
		        result = classToJson(clazz);
		        break;
		}
		
		return result;
	}
	
	public static String toJson(Class<?> clazz) throws Exception {
		ClassType classType = TypeChecker.check(clazz);
		String result = null;
		
		switch (classType) {
		    case PRIMITIVE:
		    	result = fieldToString(clazz.getName());
		    	break;
		    case WRAPPER:
		        result = fieldToString(clazz.getSimpleName());
		        break;
		    case USER_DEFINED:
		        result = classToJson(clazz);
		        break;
		}
		
		return result;
	}
	
	
	private static String fieldToString(String type) {
		return String.format("%s", type);
	}
	
	
	private static String fieldToJson(String type, String name) {
		return String.format("{\"%s\": \"%s\"}", type, name);
	}
	
	
	// 클래스 정보를 JSON 형식으로 변환
	private static String classToJson(Class<?> clazz) throws Exception {
	    ObjectNode jsonStructure = objectMapper.createObjectNode(); // Jackson의 JSON 객체 구조를 표현하는 클래스
	    
	    Field[] fields = clazz.getDeclaredFields();

	    for (Field field : fields) {
	        field.setAccessible(true);  // private 필드에 접근할 수 있도록 설정
	        
	        String fieldName = field.getName();
	        String fieldType = field.getType().getSimpleName();
	        
	        // 타입이 클래스이면 재귀적으로 처리하여 JSON에 추가
	        if (TypeChecker.check(field.getType()) == ClassType.USER_DEFINED) {
	            jsonStructure.set(fieldName, objectMapper.readTree(classToJson(field.getType())));
	        } 
	        // 일반적인 필드 타입은 필드 이름과 함께 JSON으로 추가
	        else {
	            jsonStructure.put(fieldName, fieldType);
	        }
	    }
	    
	    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonStructure);
	}	
}
