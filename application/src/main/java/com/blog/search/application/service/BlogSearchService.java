package com.blog.search.application.service;

import com.blog.search.common.dto.SearchApiRequest;
import com.blog.search.common.dto.SearchListResponse;
import com.blog.search.common.service.BlogSearchApiInterface;
import com.blog.search.common.service.BlogSearchApiServiceFactory;
import com.blog.search.common.type.ApiType;
import com.blog.search.common.type.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlogSearchService {

    @Retryable(value = {Exception.class}, maxAttempts = 1)
    @Transactional(readOnly = true)
    public SearchListResponse searchBlogs(String keyword, SortType sortType, int pageNumber, int pageSize) throws Exception {

        BlogSearchApiInterface<?> blogSearchApiService = BlogSearchApiServiceFactory.getService(ApiType.KAKAO);

        return blogSearchApiService.searchBlogDataFromApi(SearchApiRequest.of(keyword, sortType, pageNumber, pageSize));
    }

    @Recover
    @Transactional(readOnly = true)
    public SearchListResponse searchBlogs(Exception e, String keyword, SortType sortType, int pageNumber, int pageSize) throws Exception {

        BlogSearchApiInterface<?> blogSearchApiService = BlogSearchApiServiceFactory.getService(ApiType.NAVER);

        return blogSearchApiService.searchBlogDataFromApi(SearchApiRequest.of(keyword, sortType, pageNumber, pageSize));
    }


}
