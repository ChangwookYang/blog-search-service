package com.blog.search.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages = { "com.blog.search.application", "com.blog.search.common" }
)
@EntityScan("com.blog.search.common.domain")
@EnableJpaRepositories(basePackages = "com.blog.search.common.repository")
public class BlogSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogSearchApplication.class, args);
    }
}
