package com.blog.search.common.dto;


import com.blog.search.common.type.SortType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchListResponse {
    List<SearchApiResponse> blogSearchList;

    private Long totalCount;
    private int page;
    private int size;
    private Boolean IsEndPage;
    private SortType sortType;

    public static SearchListResponse of(
            List<SearchApiResponse> result, Long totalCount,
            int page, int size, Boolean IsEndPage, SortType sortType) {
        return new SearchListResponse(result, totalCount, page, size, IsEndPage, sortType);
    }
}
