package com.blog.search.application.service;

import com.blog.search.common.service.KakaoSearchApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BlogSearchServiceTest {
    @Autowired
    private KakaoSearchApiService kakaoSearchApiService;

    @Test
    void test() {

        kakaoSearchApiService.searchBlogDataFromApi("안녕");
    }
}