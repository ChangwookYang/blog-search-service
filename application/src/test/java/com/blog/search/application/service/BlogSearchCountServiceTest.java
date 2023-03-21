package com.blog.search.application.service;


import com.blog.search.application.dto.KeywordCountResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BlogSearchCountServiceTest {
    @Autowired
    private BlogSearchCountService blogSearchCountService;

    @DisplayName("인기 검색어 10개를 정상적으로 결과값을 반환한다.")
    @Test
    void givenNothing_whenSearchPopular_thenReturnsTop10() {
        int size = blogSearchCountService.findTop10OrderBySearchCountDesc().size();

        assertEquals(10, size);
    }

    @DisplayName("인기 검색어 0번 index의 count가 max값과 일치한다.")
    @Test
    void givenNothing_whenSearchPopular_thenReturnsFirstIndexEqualsMaxCount() {
        List<KeywordCountResponse> list = blogSearchCountService.findTop10OrderBySearchCountDesc();

        Optional<Long> first = list.stream().map(KeywordCountResponse::getSearchCount).max(Comparator.naturalOrder());
        assertEquals(first.get(), list.get(0).getSearchCount());
    }
}