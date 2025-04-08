package io.versionpulse.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.versionpulse.model.HttpParameter;
import lombok.Builder;

@Builder
public record UpdateDTO(
	    Parent parent,
	    Properties properties,
	    List<Child> children
	) {

	    public static record Parent(String database_id) {}

	    @Builder
	    public static record Properties(
	        Name name,
	        Description description,
	        Path path,
	        Check check,
	        Method method
	    ) {

	        public static record Name(List<Title> title) {

	            public static record Title(Text text) {

	                public static record Text(String content) {}
	            }
	            
	            public static Name of(String apiName) {
	            	return new Name(List.of(new Title(new Title.Text(apiName))));
	            }
	        }

	        public static record Description(List<RichText> rich_text) {

	            public static record RichText(Text text) {

	                public static record Text(String content) {}
	            }
	            
	            public static Description of(String description) {
	            	return new Description(List.of(new RichText(new RichText.Text(description))));
	            }
	        }

	        public static record Path(List<RichText> rich_text) {

	            public static record RichText(Text text) {

	                public static record Text(String content) {}
	            }
	            
	            public static Path of(String path) {
	            	return new Path(List.of(new RichText(new RichText.Text(path))));
	            }
	        }

	        public static record Check(boolean checkbox) {}

	        public static record Method(Select select) {

	            public static record Select(String name) {}
	            
	            public static Method of(String httpMethod) {
	            	return new Method(new Select(httpMethod));
	            }
	        }
	    }

	    @Builder
	    public static record Child(
	        String object,
	        String type,
	        @JsonInclude(value = Include.NON_NULL) Heading2 heading_2,
	        @JsonInclude(value = Include.NON_NULL) Paragraph paragraph,
	        @JsonInclude(value = Include.NON_NULL) Code code,
	        @JsonInclude(value = Include.NON_NULL) Table table,
	        @JsonInclude(value = Include.NON_NULL) TableRow table_row
	    ) {
	    	
	    	@Builder
	    	public static record Table(
	    	        int table_width,
	    	        boolean has_column_header,
	    	        boolean has_row_header,
	    	        List<Child> children // ⚠️ 여기가 TableRow가 아닌 Child여야 함
	    	    ) {}

	    	    @Builder
	    	    public static record TableRow(List<List<Cell>> cells) {

	    	        public static TableRow of(List<String> contents) {
	    	            List<List<Cell>> cells = contents.stream()
	    	                .map(content -> List.of(Cell.of(content)))
	    	                .toList();
	    	            return new TableRow(cells);
	    	        }

	    	        @Builder
	    	        public static record Cell(
	    	            String type,
	    	            Text text,
	    	            Annotations annotations,
	    	            String plain_text,
	    	            String href
	    	        ) {
	    	            public static Cell of(String content) {
	    	                return new Cell(
	    	                    "text",
	    	                    new Text(content, null),
	    	                    new Annotations(false, false, false, false, false, "default"),
	    	                    content,
	    	                    null
	    	                );
	    	            }

	    	            public static record Text(String content, String link) {}

	    	            public static record Annotations(
	    	                boolean bold,
	    	                boolean italic,
	    	                boolean strikethrough,
	    	                boolean underline,
	    	                boolean code,
	    	                String color
	    	            ) {}
	    	        }
	    	    }

	        public static record Heading2(List<RichText> rich_text) {

	            public static record RichText(String type, Text text) {

	                public static record Text(String content) {}
	            }
	            
	            public static Heading2 of(String type, String content) {
	            	return new Heading2(List.of(new RichText(type, new RichText.Text(content))));
	            }
	        }

	        public static record Paragraph(List<RichText> rich_text) {

	            public static record RichText(String type, Text text) {

	                public static record Text(String content, Link link) {

	                    public static record Link(String url) {}
	                }
	            }
	            
	            public static Paragraph of(String type, String content) {
	            	return new Paragraph(List.of(new RichText(type, new RichText.Text(content, null))));
	            }
	        }
	        
	        public static record Code(String language, List<RichText> rich_text) {
	        	public static record RichText(String type, Text text) {
	                public static record Text(String content) {
	                }
	            }
	        	public static Code of(String language, String type, String content) {
	        		return new Code(language, List.of(new RichText(type, new RichText.Text(content))));
	        	}
	        }
	        public static Child of(String object, String type, Object content) {
	        	if (content instanceof Heading2) {
	        		return Child.builder()
		        			.object(object)
		        			.type(type)
		        			.heading_2((Heading2) content)
		        			.build();
	        	}
	        	else if (content instanceof Paragraph) {
	        		return Child.builder()
		        			.object(object)
		        			.type(type)
		        			.paragraph((Paragraph) content)
		        			.build();
	        	}
	        	else if (content instanceof Code) {
	        		return Child.builder()
	        				.object(object)
	        				.type(type)
	        				.code((Code) content)
	        				.build();
	        	}
	        	else if (content instanceof Table) {
	        		return Child.builder()
	        				.object(object)
	        				.type(type)
	        				.table((Table) content)
	        				.build();
	        	}
	        	return null;
	        }
	    }
    
	    
	    public static UpdateDTO of(String databaseId, String apiName, String apiDesc, String apiPath, String httpMethod, String...strings ) {
	    	Parent parent = new Parent(databaseId);
	        Properties properties = Properties.builder()
	        		.name(Properties.Name.of(apiName))
	        		.description(Properties.Description.of(apiDesc))
	        		.path(Properties.Path.of(apiPath))
	        		.check(new Properties.Check(false))
	        		.method(Properties.Method.of(httpMethod))
	        		.build();
	        
	        // 임시
	        List<Child> contents = new ArrayList<>();
	        for (int i=0; i<strings.length; i+=2) {
	        	String type = "text";
	        	Child.Heading2 heading = Child.Heading2.of(type, strings[i]);
	        	Child child1 = Child.of("block", "heading_2", heading);
	        	Child child2;
	        	
	        	if (strings[i].equals("RequestBody") || strings[i].equals("ResponseBody")) {
	        		Child.Code code = Child.Code.of("java", type, strings[i+1]);
	        		child2 = Child.of("block", "code", code);
	        	}
	        	else {
	        		// 테이블 레코드
	        		List<Child> children = new ArrayList<>();
	        		// TableRow 생성
	        		Child.TableRow header = Child.TableRow.of(List.of("변수명", "자료형"));
	        		Child headerBlock = Child.builder()
		        		    .object("block")
		        		    .type("table_row")
		        		    .table_row(header)
		        		    .build();
	        		children.add(headerBlock);
	        		for (String params : strings[i+1].split("\n")) {
	        			if (params.length() == 0) continue;
	        			String[] info = params.split(" ");
	        			Child.TableRow row = Child.TableRow.of(List.of(info[0], info[1]));
	        			Child rowBlock = Child.builder()
	    	        		    .object("block")
	    	        		    .type("table_row")
	    	        		    .table_row(row)
	    	        		    .build();
	        			children.add(rowBlock);
	        		}
	        	
	        		// Table 생성
	        		Child.Table table = Child.Table.builder()
	        		    .table_width(2)
	        		    .has_column_header(true)
	        		    .has_row_header(false)
	        		    .children(children)
	        		    .build();
	        		child2 = Child.of("block", "table", table);
	        	}
	        	contents.add(child1);
	        	contents.add(child2);
	        }
	        
	    	return UpdateDTO.builder()
	        		.parent(parent)
	        		.properties(properties)
	        		.children(contents)
	        		.build();
	    }
}
