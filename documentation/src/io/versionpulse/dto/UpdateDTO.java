package io.versionpulse.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class UpdateDTO {
	public Parent parent;
    public Properties properties;
    public List<Child> children;
    
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    @Builder
    @AllArgsConstructor
    public static class Parent {
        public String database_id;
    }

    @Builder
    @AllArgsConstructor
    public static class Properties {
        public Name name;
        public Description description;
        public Path path;
        public Check check;
        public Method method;

        @Builder
        @AllArgsConstructor
        public static class Name {
            public List<Title> title;

            @Builder
            @AllArgsConstructor
            public static class Title {
                public Text text;

                @Builder
                @AllArgsConstructor
                public static class Text {
                    public String content;
                }
            }
        }

        @Builder
        @AllArgsConstructor
        public static class Description {
            public List<RichText> rich_text;

            @Builder
            @AllArgsConstructor
            public static class RichText {
                public Text text;

                @Builder
                @AllArgsConstructor
                public static class Text {
                    public String content;
                }
            }
        }

        @Builder
        @AllArgsConstructor
        public static class Path {
            public List<RichText> rich_text;

            @Builder
            @AllArgsConstructor
            public static class RichText {
                public Text text;

                @Builder
                @AllArgsConstructor
                public static class Text {
                    public String content;
                }
            }
        }

        @Builder
        @AllArgsConstructor
        public static class Check {
            public boolean checkbox;
        }

        @Builder
        @AllArgsConstructor
        public static class Method {
            public Select select;
            
            @Builder
            @AllArgsConstructor
            public static class Select {
                public String name;
            }
        }
    }

    @Builder
    @AllArgsConstructor
    public static class Child {
        public String object;
        public String type;
        @JsonInclude(value = Include.NON_NULL)
        public Heading2 heading_2;
        @JsonInclude(value = Include.NON_NULL)
        public Paragraph paragraph;

        @Builder
        @AllArgsConstructor
        public static class Heading2 {
            public List<RichText> rich_text;

            @Builder
            @AllArgsConstructor
            public static class RichText {
            	public String type;
                public Text text;

                @Builder
                @AllArgsConstructor
                public static class Text {
                    public String content;
                }
            }
        }

        @Builder
        @AllArgsConstructor
        public static class Paragraph {
            public List<RichText> rich_text;

            @Builder
            @AllArgsConstructor
            public static class RichText {
            	public String type;
                public Text text;

                @Builder
                @AllArgsConstructor
                public static class Text {
                    public String content;
                    public Link link;

                    @Builder
                    @AllArgsConstructor
                    public static class Link {
                        public String url;
                    }
                }
            }
        }
    }
}
