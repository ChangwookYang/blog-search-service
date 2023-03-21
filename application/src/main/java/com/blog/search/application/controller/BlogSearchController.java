package com.blog.search.application.controller;

import com.blog.search.application.dto.KeywordCountResponse;
import com.blog.search.application.service.BlogSearchCountService;
import com.blog.search.application.service.BlogSearchService;
import com.blog.search.common.type.SortType;
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

    @GetMapping("/v1/search/blog/keyword")
    public ResponseEntity<Object> searchBlogs(
            @RequestParam String keyword,
            @RequestParam(required = false, defaultValue = "ACCURACY") SortType sortType,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ) throws Exception {
        setSearchCount(keyword);
        return new ResponseEntity<>(blogSearchService.searchBlogs(keyword, sortType, pageNumber, pageSize), HttpStatus.OK);
    }

    private Long setSearchCount(String keyword) {
        // TODO redis 업데이트
        blogSearchCountService.getKeywordSearchCount(keyword);
        return 0L;
    }

    @GetMapping("/v1/search/blog/popular")
    public ResponseEntity<Object> searchPopularBlogs() {
        List<KeywordCountResponse> top10List = blogSearchCountService.findTop10OrderBySearchCountDesc();
        HttpStatus status = (top10List.isEmpty()) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(top10List, status);
    }

}
