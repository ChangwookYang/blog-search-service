package com.blog.search.common.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {

    @JsonProperty("title")
    private String title;

    @JsonProperty("contents")
    private String contents;

    @JsonProperty("url")
    private String url;

    @JsonProperty("blogname")
    private String blogname;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("dateTime")
    private LocalDateTime dateTime;
}
