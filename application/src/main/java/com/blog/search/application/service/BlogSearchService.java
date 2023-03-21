package com.blog.search.application.service;

import com.blog.search.common.dto.SearchApiRequest;
import com.blog.search.common.dto.SearchListResponse;
import com.blog.search.common.service.BlogSearchApiInterface;
import com.blog.search.common.service.BlogSearchApiServiceFactory;
import com.blog.search.common.type.ApiType;
import com.blog.search.common.type.SortType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogSearchService {

    @Retryable(value = {Exception.class}, maxAttempts = 1)
    @Transactional(readOnly = true)
    public SearchListResponse searchBlogs(String keyword, SortType sortType, int pageNumber, int pageSize) throws Exception {
        log.info("search blogs - keyword : {}, apiType : {}", keyword, ApiType.KAKAO);

        BlogSearchApiInterface<?> blogSearchApiService = BlogSearchApiServiceFactory.getService(ApiType.KAKAO);

        return blogSearchApiService.searchBlogDataFromApi(SearchApiRequest.of(keyword, sortType, pageNumber, pageSize));
    }

    @Recover
    @Transactional(readOnly = true)
    public SearchListResponse searchBlogs(Exception e, String keyword, SortType sortType, int pageNumber, int pageSize) throws Exception {
        log.info("search blogs recover - keyword : {}, apiType : {}", keyword, ApiType.NAVER);

        BlogSearchApiInterface<?> blogSearchApiService = BlogSearchApiServiceFactory.getService(ApiType.NAVER);

        return blogSearchApiService.searchBlogDataFromApi(SearchApiRequest.of(keyword, sortType, pageNumber, pageSize));
    }


}
