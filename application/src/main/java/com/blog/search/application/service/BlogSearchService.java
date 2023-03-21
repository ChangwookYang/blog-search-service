package com.blog.search.application.service;

import com.blog.search.common.dto.SearchApiRequest;
import com.blog.search.common.dto.SearchListResponse;
import com.blog.search.common.service.BlogSearchApiInterface;
import com.blog.search.common.service.BlogSearchApiServiceFactory;
import com.blog.search.common.type.ApiType;
import com.blog.search.common.type.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlogSearchService {

    @Transactional(readOnly = true)
    public SearchListResponse searchBlogs(String keyword, SortType sortType, int pageNumber, int pageSize) throws Exception {
        BlogSearchApiInterface<?> blogSearchApiService = BlogSearchApiServiceFactory.getService(ApiType.KAKAO);

        // TODO 에러 발생 시 Naver @Retryable
        return blogSearchApiService.searchBlogDataFromApi(
                SearchApiRequest.of(keyword, sortType, pageNumber, pageSize));
    }

}
