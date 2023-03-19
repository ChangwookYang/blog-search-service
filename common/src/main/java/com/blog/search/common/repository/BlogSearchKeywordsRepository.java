package com.blog.search.common.repository;

import com.blog.search.common.domain.BlogSearchKeywords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogSearchKeywordsRepository extends JpaRepository<BlogSearchKeywords, Long> {
    
}
