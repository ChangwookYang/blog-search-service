package com.blog.search.common.service;

import com.blog.search.common.type.ApiType;

public interface BlogSearchApiInterface<T> {
    ApiType getApiTye();

    T searchBlogDataFromApi(String keyword);

    T searchBlogDataFromDatabase(String jsonResult) throws Exception;
}
