package io.versionpulse.dto;

import java.util.List;

import io.versionpulse.dto.record.Check;
import io.versionpulse.dto.record.Child;
import io.versionpulse.dto.record.Description;
import io.versionpulse.dto.record.Method;
import io.versionpulse.dto.record.Name;
import io.versionpulse.dto.record.Parent;
import io.versionpulse.dto.record.Path;
import io.versionpulse.dto.record.Properties;
import lombok.Builder;

@Builder
public record UpdateDTO(
	    Parent parent,
	    Properties properties,
	    List<Child> children
	) 
{
    public static UpdateDTO of(String databaseId, String apiName, String apiDesc, String apiPath, String httpMethod, Child...children ) {
    	Parent parent = new Parent(databaseId);
        Properties properties = Properties.builder()
        		.name(Name.of(apiName))
        		.description(Description.of(apiDesc))
        		.path(Path.of(apiPath))
        		.check(new Check(false))
        		.method(Method.of(httpMethod))
        		.build();
        
        List<Child> contents = List.of(children);
               
    	return UpdateDTO.builder()
        		.parent(parent)
        		.properties(properties)
        		.children(contents)
        		.build();
    }
}
