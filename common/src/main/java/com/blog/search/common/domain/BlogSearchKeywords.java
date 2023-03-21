package com.blog.search.common.domain;

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
public class BlogSearchKeywords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false, length = 50)
    private String keyword;
    @Setter @Column(nullable = false)
    private Long searchCount;

    @CreatedDate @Column(nullable = false, updatable = false)
    private LocalDateTime createdDt;
    @LastModifiedDate @Column(nullable = false)
    private LocalDateTime updatedDt;

    protected BlogSearchKeywords() {}

    private BlogSearchKeywords(String keyword, Long searchCount) {
        this.keyword = keyword;
        this.searchCount = searchCount;
    }

    public static BlogSearchKeywords of(String keyword, Long searchCount) {
        return new BlogSearchKeywords(keyword, searchCount);
    }
}
