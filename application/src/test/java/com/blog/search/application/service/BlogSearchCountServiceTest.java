package com.blog.search.application.service;


import com.blog.search.application.dto.KeywordCountResponse;
import com.blog.search.common.domain.BlogSearchKeywords;
import com.blog.search.common.repository.BlogSearchKeywordsRepository;
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

    @Autowired
    private BlogSearchKeywordsRepository keywordsRepository;

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
        assertEquals(first.orElse(0L), list.get(0).getSearchCount());
    }

    @DisplayName("Redis 미연동 시에 db count 1 증가")
    @Test
    void givenKeyword_whenRedisIsNotWorking_thenIncrementSearchCount() {
        // given
        String keyword = "안녕";
        Long searchCount = 10L;

        BlogSearchKeywords searchKeywords = keywordsRepository.findByKeyword(keyword);
        if (searchKeywords != null) {
            searchCount = searchKeywords.getSearchCount();
        } else {
            keywordsRepository.save(BlogSearchKeywords.of(keyword, searchCount));
        }

        // when
        blogSearchCountService.setKeywordSearchCount(keyword);

        // then
        BlogSearchKeywords afterSearchKeywords = keywordsRepository.findByKeyword(keyword);
        assertEquals(searchCount + 1, afterSearchKeywords.getSearchCount());
    }

}