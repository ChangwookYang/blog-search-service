package com.blog.search.application.service;

import com.blog.search.common.service.BlogSearchApiInterface;
import com.blog.search.common.service.BlogSearchApiServiceFactory;
import com.blog.search.common.type.ApiType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlogSearchService {

    @Transactional(readOnly = true)
    public Object searchBlogs(String keyword, Pageable pageable) throws Exception {
        BlogSearchApiInterface<?> blogSearchApiService = BlogSearchApiServiceFactory.getService(ApiType.NAVER);

        // TODO 에러 발생 시 Naver
        return blogSearchApiService.searchBlogDataFromApi(keyword);
    }

}
