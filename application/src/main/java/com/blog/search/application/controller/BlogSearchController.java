package com.blog.search.application.controller;

import com.blog.search.application.dto.KeywordCountResponse;
import com.blog.search.application.service.BlogSearchCountService;
import com.blog.search.application.service.BlogSearchService;
import com.blog.search.common.dto.SearchListResponse;
import com.blog.search.common.exception.ApiRuntimeException;
import com.blog.search.common.type.SortType;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogSearchController {

    private final BlogSearchService blogSearchService;

    private final BlogSearchCountService blogSearchCountService;

    @ApiOperation(value = "블로그 목록 키워드 검색", notes = "키워드를 검색을 통해 블로그 목록을 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "키워드", required = true),
            @ApiImplicitParam(name = "sortType", value = "검색 유형", required = false),
            @ApiImplicitParam(name = "pageNumber", value = "페이지 넘버(디폹트:0)", required = false),
            @ApiImplicitParam(name = "pageSize", value = "페이지 사이즈(디폴트:10)", required = false)
    })
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200, message = "성공", response = SearchListResponse.class)
            }
    )
    @GetMapping("/api/search")
    public ResponseEntity<Object> searchBlogs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "ACCURACY") SortType sortType,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ) throws Exception {
        if (keyword == null) throw new ApiRuntimeException(HttpStatus.BAD_REQUEST, "Keyword is null");

        blogSearchCountService.setKeywordSearchCount(keyword);
        return new ResponseEntity<>(blogSearchService.searchBlogs(keyword, sortType, pageNumber, pageSize), HttpStatus.OK);
    }

    @ApiResponses(
            value = {@ApiResponse(code = 200, message = "성공", response = KeywordCountResponse.class)}
    )
    @GetMapping("/api/popular")
    @ApiOperation(value = "블로그 키워드 검색 순 TOP 10", notes = "블로그 키워드 검색 순 Top 10을 조회한다.")
    public ResponseEntity<Object> searchPopularBlogs() {
        List<KeywordCountResponse> top10List = blogSearchCountService.findTop10OrderBySearchCountDesc();
        HttpStatus status = (top10List.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(top10List, status);
    }

}
