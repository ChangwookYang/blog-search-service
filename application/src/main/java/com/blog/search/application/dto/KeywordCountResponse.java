package com.blog.search.application.dto;

import com.blog.search.common.domain.BlogSearchKeywords;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KeywordCountResponse {
    String keyword;
    Long searchCount;

    public static KeywordCountResponse of(String keyword, Long searchCount) {
        return new KeywordCountResponse(keyword, searchCount);
    }

    public static KeywordCountResponse from(BlogSearchKeywords entity) {
        return new KeywordCountResponse(entity.getKeyword(), entity.getSearchCount());
    }
}
