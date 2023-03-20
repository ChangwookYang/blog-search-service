package com.blog.search.application.service;

import com.blog.search.application.dto.KeywordCountResponse;
import com.blog.search.common.repository.BlogSearchKeywordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogSearchCountService {

    private final BlogSearchKeywordsRepository blogSearchKeywordsRepository;

    public void getKeywordSearchCount(String keyword) {
        // redis

        // DB 비교
    }

    @Transactional(readOnly = true)
    public List<KeywordCountResponse> findTop10OrderBySearchCountDesc() {
        return blogSearchKeywordsRepository.findTop10ByOrderBySearchCountDesc().stream().map(KeywordCountResponse::from).collect(Collectors.toList());
    }
}
