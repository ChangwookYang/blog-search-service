package com.blog.search.common.service;

import com.blog.search.common.type.ApiType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BlogSearchApiServiceFactory {

    private final List<BlogSearchApiInterface<?>> services;

    private static final Map<ApiType, BlogSearchApiInterface<?>> blogSearchApiMap = new HashMap<>();

    @PostConstruct
    public void initBlogSearchApi() {
        for (BlogSearchApiInterface<?> service : services) {
            blogSearchApiMap.put(service.getApiTye(), service);
        }
    }

    public static BlogSearchApiInterface<?> getService(ApiType apiType) {
        return blogSearchApiMap.get(apiType);
    }
}
