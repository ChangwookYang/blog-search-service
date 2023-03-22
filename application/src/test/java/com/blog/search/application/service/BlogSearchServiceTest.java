package com.blog.search.application.service;

import com.blog.search.common.dto.SearchListResponse;
import com.blog.search.common.exception.ApiRuntimeException;
import com.blog.search.common.type.ApiType;
import com.blog.search.common.type.SortType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BlogSearchServiceTest {
    @Autowired
    private BlogSearchService blogSearchService;

    @DisplayName("[블로그 키워드 검색] 키워드 검색하면, 페이지 사이즈만큼 정상적으로 결과값을 반환한다.")
    @Test
    void givenKeywordAndPage_whenSearchBlog_thenReturnsResults() throws Exception {
        // given
        String keyword = "안녕";
        int pageNumber = 0;
        int pageSize = 10;

        // when
        SearchListResponse response = blogSearchService.searchBlogs(keyword, SortType.ACCURACY, pageNumber, pageSize);

        // then
        assertEquals(pageSize, response.getBlogSearchList().size());
        assertEquals(pageNumber, response.getPage());
        assertEquals(pageSize, response.getSize());
        assertEquals(SortType.ACCURACY, response.getSortType());
    }

    @DisplayName("[블로그 키워드 검색] 키워드 없이 검색하면, BAD_REQUEST 에러를 반환한다.")
    @Test
    void givenNothing_whenSearchBlog_thenReturnsBadRequest() throws Exception {
        // given
        String keyword = null;

        // when
        ApiRuntimeException ae = assertThrows(ApiRuntimeException.class,
                () -> blogSearchService.searchBlogs(keyword, SortType.ACCURACY, 0, 10));

        // then
        assertEquals(HttpStatus.BAD_REQUEST, ae.getHttpStatus());
    }

    @DisplayName("[블로그 키워드 검색] 키워드 정상 조회 시에 카카오 검색을 통해 결과값을 반환한다.")
    @Test
    void givenNormalRequest_whenSearchBlog_thenReturnsFromKakao() throws Exception {
        // given
        String keyword = "안녕";
        int pageNumber = 0;
        int pageSize = 10;

        // when
        SearchListResponse response = blogSearchService.searchBlogs(keyword, SortType.ACCURACY, pageNumber, pageSize);

        // then
        assertEquals(ApiType.KAKAO, response.getApiType());
    }

    @DisplayName("[블로그 키워드 검색] 카카오 검색 에러 시에 네이버 검색을 통하여 결과 반환한다.")
    @Test
    void givenRequest_whenSearchBlogAndKakaoError_thenReturnsFromNaver() throws Exception {

        // given
        String keyword = "안녕";
        int pageNumber = 0;
        int pageSize = 80; // kakao 가능 사이즈는 1~50

        // when
        SearchListResponse response = blogSearchService.searchBlogs(keyword, SortType.ACCURACY, pageNumber, pageSize);

        // then
        assertEquals(ApiType.NAVER, response.getApiType());
    }

}