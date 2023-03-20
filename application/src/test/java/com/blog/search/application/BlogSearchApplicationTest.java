package com.blog.search.application;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = BlogSearchApplication.class)
class BlogSearchApplicationTest {
    @Test
    void contextLoads() {
    }
}