package com.blog.search.application.service;

import com.blog.search.common.dto.SearchApiRequest;
import com.blog.search.common.service.KakaoSearchApiService;
import com.blog.search.common.type.SortType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogSearchServiceTest {
    @Autowired
    private KakaoSearchApiService kakaoSearchApiService;

    @Test
    void test() throws Exception {
        kakaoSearchApiService.searchBlogDataFromApi(SearchApiRequest.of("안녕", SortType.ACCURACY, 1,10));
    }
}