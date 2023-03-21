package com.blog.search.application.controller;

import com.blog.search.common.type.SortType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("블로그 검색 컨트롤러 테스트")
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
class BlogSearchControllerTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("[GET /api/search] 블로그 검색 - 정상 조회 및 페이징 일치")
    @Test
    public void givenKeyword_whenSearchBlog_thenReturnBlogList() throws Exception {
        // given
        String keyword = "안녕";
        String pageNumber = "1";
        String pageSize = "5";
        String sortType = SortType.ACCURACY.toString();
        // when
        mvc.perform(get("/api/search")
                                .param("keyword", keyword)
                                .param("pageNumber", pageNumber)
                                .param("pageSize", pageSize)
                                .param("sortType", sortType)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.page").value(pageNumber))
                .andExpect(jsonPath("$.size").value(pageSize))
                .andExpect(jsonPath("$.sortType").value(sortType))
                .andExpect(jsonPath("$.blogSearchList").isNotEmpty())
                .andReturn();
    }

    @DisplayName("[GET /api/search] 블로그 검색 - 요청값 400 에러")
    @Test
    public void givenKeywordAndPageSize1000_whenSearchBlogList_thenReturnBlogList() throws Exception {
        // given
        String keyword = "안녕";
        String pageNumber = "1";
        String pageSize = "1000";
        String sortType = SortType.ACCURACY.toString();

        // when
        mvc.perform(get("/api/search")
                                .param("keyword", keyword)
                                .param("pageNumber", pageNumber)
                                .param("pageSize", pageSize)
                                .param("sortType", sortType)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(400))
                .andReturn();
    }

    @DisplayName("[GET /api/search] 블로그 검색 - 결과 없음")
    @Test
    public void givenWeiredKeyword_whenSearchBlogList_thenReturnEmpty() throws Exception {
        // given
        String keyword = "안asdfasdfqacssmksdklcsaklacsml녕";
        String pageNumber = "1";
        String pageSize = "10";
        String sortType = SortType.ACCURACY.toString();

        // when
        mvc.perform(get("/api/search")
                                .param("keyword", keyword)
                                .param("pageNumber", pageNumber)
                                .param("pageSize", pageSize)
                                .param("sortType", sortType)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.page").value(pageNumber))
                .andExpect(jsonPath("$.size").value(pageSize))
                .andExpect(jsonPath("$.sortType").value(sortType))
                .andExpect(jsonPath("$.blogSearchList").isEmpty())
                .andReturn();

    }

    @DisplayName("[GET /api/search] 블로그 검색 정렬 - 디폴트:정확도순")
    @Test
    public void givenKeywordAndNothingForSortType_whenSearchBlog_thenReturnBlogListSortedByACCURACY() throws Exception {
        // given
        String keyword = "안녕";
        String pageNumber = "1";
        String pageSize = "5";
        // when
        mvc.perform(get("/api/search")
                        .param("keyword", keyword)
                        .param("pageNumber", pageNumber)
                        .param("pageSize", pageSize)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.page").value(pageNumber))
                .andExpect(jsonPath("$.size").value(pageSize))
                .andExpect(jsonPath("$.sortType").value(SortType.ACCURACY.toString()))
                .andExpect(jsonPath("$.blogSearchList").isNotEmpty())
                .andReturn();
    }


    @DisplayName("[GET /api/search] 블로그 검색 정렬 - 최신순")
    @Test
    public void givenKeywordAndRecencySortType_whenSearchBlog_thenReturnBlogListSortedByACCURACY() throws Exception {
        // given
        String keyword = "안녕";
        String pageNumber = "1";
        String pageSize = "5";
        String sortType = SortType.RECENCY.toString();
        // when
        mvc.perform(get("/api/search")
                        .param("keyword", keyword)
                        .param("pageNumber", pageNumber)
                        .param("pageSize", pageSize)
                        .param("sortType", sortType)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.page").value(pageNumber))
                .andExpect(jsonPath("$.size").value(pageSize))
                .andExpect(jsonPath("$.sortType").value(sortType))
                .andExpect(jsonPath("$.blogSearchList").isNotEmpty())
                .andReturn();
    }

    @DisplayName("[GET /api/search] 블로그 검색 페이징 - 디폴트값 확인")
    @Test
    public void givenKeywordAndNothing_whenSearchBlog_thenReturnDefaultPagination() throws Exception {
        // given
        String keyword = "안녕";
        String defaultPageNumber = "0";
        String defaultPageSize = "10";
        // when
        mvc.perform(get("/api/search")
                        .param("keyword", keyword)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.page").value(defaultPageNumber))
                .andExpect(jsonPath("$.size").value(defaultPageSize))
                .andExpect(jsonPath("$.sortType").value(SortType.ACCURACY.toString()))
                .andExpect(jsonPath("$.blogSearchList").isNotEmpty())
                .andReturn();
    }


    @DisplayName("[GET /api/popular] 인기 키워드 검색 - 정상 조회")
    @Test
    public void givenNothing_whenRequestPopular_thenReturnPopularList() throws Exception {
        mvc.perform(get("/api/popular"))
                .andExpect(status().isOk());
    }
}