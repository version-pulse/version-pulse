package io.versionpulse.dto;

import lombok.Builder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
public record CreateDTO(
    Parent parent,
    Icon icon,
    List<Title> title,
    Properties properties,
    Boolean is_inline
) {
    public static record Parent(
        String type,
        String page_id
    ) {
    	public static Parent defaultValueOf(String pageId) {
    		return new Parent("page_id", pageId);
    	}
    }

    public static record Icon(
        String type,
        String emoji
    ) {
    	public static Icon defaultValue() {
    		return new Icon("emoji", "📝");
    	}
    }

    public static record Title(
        String type,
        Text text
    ) {
        public static record Text(
            String content,
            String link
        ) {}
        public static Title defaultValue() {
        	return new Title("text", new Text("API 명세서", null));
        }
    }

    @Builder
    public static record Properties(
        Name name,
        Description description,
        Check check,
        Method method,
        Path path,
        By by
    ) {
        
        public static record Path(
            Map<String, Object> rich_text
        ) {
        	public static Path defaultValue() {
        		return new Path(new HashMap<>());
        	}
        }

        
        public static record Name(
            Map<String, Object> title
        ) {
        	public static Name defaultValue() {
        		return new Name(new HashMap<>());
        	}
        }

        
        public static record Description(
            Map<String, Object> rich_text
        ) {
        	public static Description defaultValue() {
        		return new Description(new HashMap<>());
        	}
        }

        
        public static record Check(
            Map<String, Object> checkbox
        ) {
        	public static Check defaultValue() {
        		return new Check(new HashMap<>());
        	}
        }

        public static record Method(
            Select select
        ) {
            public static record Select(
                List<Option> options
            ) {
                public static record Option(
                    String name,
                    String color
                ) {}
            }
            public static Method defaultValue() {
            	return new Method(
            		new Select(
            			Arrays.asList(
	        			new Select.Option("GET", "green"),
	        			new Select.Option("POST", "blue"),
	        			new Select.Option("PATCH", "yellow"),
	        			new Select.Option("PUT", "orange"),
	        			new Select.Option("DELETE", "red")
	        			)
            		));
            }
        }


        public static record By(
            Map<String, Object> people
        ) {
        	public static By defaultValue() {
        		return new By(new HashMap<>());
        	}
        }
    }
    
    
    public static CreateDTO of(String pageId) {
        return CreateDTO.builder()
                .icon(Icon.defaultValue())
                .parent(Parent.defaultValueOf(pageId))
                .title(Arrays.asList(Title.defaultValue()))
                .is_inline(true)
                .properties(Properties.builder()
                        .by(Properties.By.defaultValue())
                        .check(Properties.Check.defaultValue())
                        .name(Properties.Name.defaultValue())
                        .method(Properties.Method.defaultValue())
                        .path(Properties.Path.defaultValue())
                        .description(Properties.Description.defaultValue())
                        .build())
                .build();
    }
}
