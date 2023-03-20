package com.blog.search.common.repository;

import com.blog.search.common.domain.BlogSearchResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogSearchResultsRepository extends JpaRepository<BlogSearchResults, Long> {

    BlogSearchResults findByKeyword(String keyword);
}
