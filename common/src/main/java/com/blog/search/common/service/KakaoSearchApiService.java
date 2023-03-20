package com.blog.search.common.service;

import com.blog.search.common.dto.kakao.KakaoSearchResponse;
import com.blog.search.common.type.ApiType;
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
public class KakaoSearchApiService implements BlogSearchApiInterface<KakaoSearchResponse>{

    private final RestTemplate restTemplate;

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    @Value("${kakao.rest.api.url}")
    private String kakaoRestApiUrl;

    @Override
    public ApiType getApiTye() {
        return ApiType.KAKAO;
    }

    @Override
    public KakaoSearchResponse searchBlogDataFromApi(String keyword) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoRestApiUrl);
        uriBuilder.queryParam("query", keyword);
        URI uri = uriBuilder.build().encode().toUri();
        log.info("keyword : {}, uri : {}", keyword, uri);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
        return restTemplate.exchange(uri, HttpMethod.GET
                , new HttpEntity<>(headers), KakaoSearchResponse.class).getBody();
    }

    @Override
    public KakaoSearchResponse searchBlogDataFromDatabase(String jsonResult) throws Exception {

        return null;
    }
}
