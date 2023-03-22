package com.blog.search.common.dto;


import com.blog.search.common.type.ApiType;
import com.blog.search.common.type.SortType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(value = "블로그 검색 결과", description = "블로그 검색과 Pagination 결과 응답 Class")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchListResponse {
    @ApiModelProperty(dataType="List", value = "블로그 검색 목록")
    List<SearchApiResponse> blogSearchList;

    @ApiModelProperty(example = "KAKAO", value = "검색 API")
    private ApiType apiType;
    @ApiModelProperty(example = "100", value = "총 검색 결과 수")
    private Long totalCount;
    private int page;
    private int size;
    private Boolean isEndPage;
    private SortType sortType;

    public static SearchListResponse of(
            List<SearchApiResponse> result, ApiType apiType,
            Long totalCount, int page, int size, Boolean isEndPage, SortType sortType) {
        return new SearchListResponse(result, apiType, totalCount, page, size, isEndPage, sortType);
    }
}
