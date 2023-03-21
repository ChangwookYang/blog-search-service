package com.blog.search.common.dto;

import com.blog.search.common.type.SortType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchApiRequest {
    String keyword;
    SortType sortType;
    Integer pageNumber;
    Integer pageSize;

    public static SearchApiRequest of(String keyword, SortType sortType, Integer pageNumber, Integer pageSize) {
        return new SearchApiRequest(keyword, sortType, pageNumber, pageSize);
    }
}
