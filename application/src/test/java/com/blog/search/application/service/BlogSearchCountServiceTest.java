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
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("인기 키워드 조회, 검색 수 카운트 테스트 서비스")
@SpringBootTest
class BlogSearchCountServiceTest {
    @Autowired
    private BlogSearchCountService blogSearchCountService;

    @Autowired
    private BlogSearchKeywordsRepository keywordsRepository;

    @DisplayName("[인기 키워드 조회] 인기 검색어 10개를 정상적으로 결과값을 반환한다.")
    @Test
    void givenNothing_whenSearchPopular_thenReturnsTop10() {

        for (int i = 1; i < 20; i++) {
            keywordsRepository.save(BlogSearchKeywords.of("안녕" + i, 100L + i));
        }

        int size = blogSearchCountService.findTop10OrderBySearchCountDesc().size();

        assertEquals(10, size);
    }

    @DisplayName("[인기 키워드 조회] 인기 검색어 0번 index의 count가 max값과 일치한다.")
    @Test
    void givenFirstIndex_whenSearchPopular_thenReturnsFirstIndexEqualsMaxCount() {
        keywordsRepository.save(BlogSearchKeywords.of("안녕1", 101L));
        keywordsRepository.save(BlogSearchKeywords.of("안녕2", 10L));
        keywordsRepository.save(BlogSearchKeywords.of("안녕3", 1010L));
        keywordsRepository.save(BlogSearchKeywords.of("안녕4", 1L));
        keywordsRepository.save(BlogSearchKeywords.of("안녕5", 110L));
        List<KeywordCountResponse> list = blogSearchCountService.findTop10OrderBySearchCountDesc();

        Optional<Long> first = list.stream().map(KeywordCountResponse::getSearchCount).max(Comparator.naturalOrder());
        assertEquals(first.orElse(0L), list.get(0).getSearchCount());
    }

    @DisplayName("[인기 키워드 조회] 인기 검색어 목록이 없을 경우 빈값을 반환한다.")
    @Test
    void givenNothing_whenSearchPopular_thenReturnsFirstIndexEqualsMaxCount() {
        keywordsRepository.deleteAll();
        List<KeywordCountResponse> list = blogSearchCountService.findTop10OrderBySearchCountDesc();

        assertTrue(list.isEmpty());
    }

    @DisplayName("[키워드 조회 시 검색 수 카운트] Redis 미연동 시에 db count 1 증가")
    @Test
    void givenKeyword_whenRedisIsNotWorking_thenIncrementSearchCount() {
        // given
        String keyword = "안녕1234";
        Long searchCount = 100L;

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