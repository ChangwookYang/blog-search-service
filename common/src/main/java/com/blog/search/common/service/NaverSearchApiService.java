package com.blog.search.common.service;

import com.blog.search.common.dto.kakao.KakaoSearchResponse;
import com.blog.search.common.dto.naver.NaverSearchResponse;
import com.blog.search.common.type.ApiType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Service
public class NaverSearchApiService implements BlogSearchApiInterface<NaverSearchResponse> {
    private final RestTemplate restTemplate;

    @Value("${naver.rest.api.client-id}")
    private String naverRestApiClientId;

    @Value("${naver.rest.api.client-secret}")
    private String naverRestApiClientSecret;

    @Value("${naver.rest.api.url}")
    private String naverRestApiUrl;

    @Override
    public ApiType getApiTye() {
        return ApiType.NAVER;
    }

    @Override
    public NaverSearchResponse searchBlogDataFromApi(String keyword) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(naverRestApiUrl);
        uriBuilder.queryParam("query", keyword);
        URI uri = uriBuilder.build().encode().toUri();
        log.info("keyword : {}, uri : {}", keyword, uri);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverRestApiClientId);
        headers.set("X-Naver-Client-Secret", naverRestApiClientSecret);
        return restTemplate.exchange(uri, HttpMethod.GET
                , new HttpEntity<>(headers), NaverSearchResponse.class).getBody();
    }

    @Override
    public NaverSearchResponse searchBlogDataFromDatabase(String jsonResult) throws Exception {
        return null;
    }
}
