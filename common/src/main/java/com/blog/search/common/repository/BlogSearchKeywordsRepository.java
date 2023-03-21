package com.blog.search.common.repository;

import com.blog.search.common.domain.BlogSearchKeywords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogSearchKeywordsRepository extends JpaRepository<BlogSearchKeywords, Long> {

    BlogSearchKeywords findByKeyword(String keyword);

    List<BlogSearchKeywords> findTop10ByOrderBySearchCountDesc();
}
