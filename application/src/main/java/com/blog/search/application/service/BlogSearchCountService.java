package com.blog.search.application.service;

import com.blog.search.application.dto.KeywordCountResponse;
import com.blog.search.common.domain.BlogSearchKeywords;
import com.blog.search.common.repository.BlogSearchKeywordsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BlogSearchCountService {

    private final BlogSearchKeywordsRepository blogSearchKeywordsRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional(readOnly = true)
    public List<KeywordCountResponse> findTop10OrderBySearchCountDesc() {
        return blogSearchKeywordsRepository.findTop10ByOrderBySearchCountDesc().stream().map(KeywordCountResponse::from).collect(Collectors.toList());
    }

    @Transactional
    public void setKeywordSearchCount(String keyword) {
        BlogSearchKeywords searchKeyword = blogSearchKeywordsRepository.findByKeyword(keyword);
        Long dbCount = (searchKeyword == null) ? 0L : searchKeyword.getSearchCount();

        try {
            ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
            long searchCount = (valueOperations.get(keyword) == null) ? 0L : Long.parseLong(String.valueOf(valueOperations.get(keyword)));
            log.info("db searchCount : {}, redis searchCount : {}", dbCount, searchCount);

            if (searchCount < dbCount) {
                searchCount = dbCount;
            }

            searchCount = searchCount + 1;
            valueOperations.set(keyword, searchCount);

            // 검색횟수 10번까지는 DB 동기화
            // 검색횟수 10 ~ 100번까지 5번마다 DB 동기화
            // 검색횟수 100 ~ 1000번까지 10번마다 DB 동기화
            // 검색횟수 1000부터는 100번마다 DB 동기화
            if (searchCount < 10
                    || (searchCount < 100 && searchCount % 5 == 0)
                    || (searchCount < 1000 && searchCount % 10 == 0)
                    || searchCount % 100 == 0) {
                // DB 동기화
                saveSearchKeywordsOnDatabase(keyword, searchKeyword, searchCount);
            }
        } catch (Exception e) {
            log.error("redis not works");
            // Redis 미연동 시에 DB 업데이트
            saveSearchKeywordsOnDatabase(keyword, searchKeyword, dbCount + 1);
        }
    }

    private void saveSearchKeywordsOnDatabase(String keyword, BlogSearchKeywords searchKeyword, Long saveSearchCount) {
        log.info("database searchCount : " + saveSearchCount);
        if (searchKeyword == null) {
            searchKeyword = BlogSearchKeywords.of(keyword, saveSearchCount);
        } else {
            searchKeyword.setSearchCount(saveSearchCount);
        }
        blogSearchKeywordsRepository.save(searchKeyword);
    }
}
