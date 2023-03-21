package com.blog.search.common.service;

import com.blog.search.common.dto.SearchApiRequest;
import com.blog.search.common.dto.SearchListResponse;
import com.blog.search.common.type.ApiType;

public interface BlogSearchApiInterface<T> {
    ApiType getApiTye();

    SearchListResponse searchBlogDataFromApi(SearchApiRequest request);
}
