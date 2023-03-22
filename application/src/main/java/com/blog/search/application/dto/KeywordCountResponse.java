package com.blog.search.application.dto;

import com.blog.search.common.domain.BlogSearchKeywords;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "인기 키워드 결과", description = "인기 키워드 응답 Class")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KeywordCountResponse {
    @ApiModelProperty(example = "맛집", value = "키워드")
    String keyword;
    @ApiModelProperty(example = "10", value = "키워드 검색 수")
    Long searchCount;

    public static KeywordCountResponse from(BlogSearchKeywords entity) {
        return new KeywordCountResponse(entity.getKeyword(), entity.getSearchCount());
    }
}
