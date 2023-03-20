package com.blog.search.application.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("블로그 검색 컨트롤러 테스트")
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class BlogSearchControllerTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("[API][GET] 키워드로 블로그 검색 결과 없음")
    @Test
    public void givenKeyword_whenSearchBlogList_thenReturnBlogList() throws Exception {
        // given

        // when
        mvc.perform(get("/v1/search/blog/keyword")
                                .param("keyword", "안녕")
                                .param("page", "1")
                                .param("size", "5")
//                                .param("sort", "createdDate,desc")
                )
                .andExpect(status().isOk());
        // then

    }

//    @DisplayName("[API][GET] 키워드로 블로그 검색 (디폴트:정확도순)")
//    @DisplayName("[API][GET] 키워드로 블로그 검색 (정확도순)")
//    @Test
//    public void given

//    @DisplayName("[API][GET] 키워드로 블로그 검색 (최신순)")
//
//    @DisplayName("[API][GET] 키워드로 블로그 검색 결과 없는 케이스")
//
//    @DisplayName("[API][GET] 인기있는 검색어 목록 조회")
}