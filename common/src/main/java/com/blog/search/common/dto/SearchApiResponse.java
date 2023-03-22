package com.blog.search.common.dto;

import com.blog.search.common.dto.kakao.DocumentDto;
import com.blog.search.common.dto.naver.ItemDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ApiModel(value = "블로그 글 응답", description = "블로그 글 응답 Class")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchApiResponse {

    @ApiModelProperty(value = "블로그 글 제목", example = "블로그 글 제목")
    private String title;
    @ApiModelProperty(value = "블로그 글 내용", example = "블로그 글 내용")
    private String contents;
    @ApiModelProperty(value = "블로그 글 URL", example = "블로그 글 URL")
    private String url;
    @ApiModelProperty(value = "블로그 이름", example = "블로그 이름")
    private String blogName;
    @ApiModelProperty(value = "블로그 썸네일", example = "Image URL")
    private String thumbnail;
    @ApiModelProperty(value = "블로그 생성 일자", example = "2033-03-22")
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
        return SearchApiResponse.of(itemDto.getTitle(), itemDto.getDescription(), itemDto.getLink(), itemDto.getBloggername()
                , null, LocalDate.parse(itemDto.getPostdate(), formatter));
    }

}
