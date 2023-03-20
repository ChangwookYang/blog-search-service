package com.blog.search.common.domain;

import com.blog.search.common.type.ApiType;
import com.blog.search.common.type.SearchType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "keyword")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class BlogSearchResults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false, length = 50)
    private String keyword;
    @Setter @Column(nullable = false, columnDefinition = "json")
    private String searchResult;
    @Setter @Column(nullable = false, length = 10) @Enumerated(EnumType.STRING)
    private SearchType searchType;
    @Setter @Column(nullable = false, length = 10) @Enumerated(EnumType.STRING)
    private ApiType apiType;

    @CreatedDate @Column(nullable = false)
    private LocalDateTime createdDt;
    @LastModifiedDate @Column(nullable = false)
    private LocalDateTime updatedDt;

    protected BlogSearchResults() {}

    private BlogSearchResults(String keyword, String searchResult, SearchType searchType, ApiType apiType) {
        this.keyword = keyword;
        this.searchResult = searchResult;
        this.searchType = searchType;
        this.apiType = apiType;
    }

    public static BlogSearchResults of(String keyword, String searchResult, SearchType searchType, ApiType apiType) {
        return new BlogSearchResults(keyword, searchResult, searchType, apiType);
    }
}

