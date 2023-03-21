package com.blog.search.common.dto;

import com.blog.search.common.dto.kakao.DocumentDto;
import com.blog.search.common.dto.naver.ItemDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchApiResponse {
    private String title;
    private String contents;
    private String url;
    private String blogName;
    private String thumbnail;
    private LocalDate createdDate;

    public static SearchApiResponse of(String title, String contents, String url, String blogName, String thumbnail, LocalDate createdDate) {
        return new SearchApiResponse(title, contents, url, blogName, thumbnail, createdDate);
    }

    public static SearchApiResponse of(DocumentDto documentDto) {
        LocalDate createdDate = LocalDate.parse(documentDto.getDateTime().substring(0, 10));
        return SearchApiResponse.of(documentDto.getTitle(), documentDto.getContents(), documentDto.getUrl(), documentDto.getBlogname()
                , documentDto.getThumbnail(), createdDate);
    }
    
    public static SearchApiResponse of(ItemDto itemDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate createdDate = LocalDate.parse(itemDto.getPostdate(), formatter);
        return SearchApiResponse.of(itemDto.getTitle(), itemDto.getDescription(), itemDto.getLink(),
                itemDto.getBloggername(), null, createdDate);
    }
}
