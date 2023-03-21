package com.blog.search.common.service;

import com.blog.search.common.dto.SearchApiRequest;
import com.blog.search.common.dto.SearchApiResponse;
import com.blog.search.common.dto.SearchListResponse;
import com.blog.search.common.dto.kakao.KakaoSearchResponse;
import com.blog.search.common.exception.ApiRuntimeException;
import com.blog.search.common.type.ApiType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoSearchApiService implements BlogSearchApiInterface<KakaoSearchResponse> {

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
    public SearchListResponse searchBlogDataFromApi(SearchApiRequest request) throws Exception {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoRestApiUrl);
        uriBuilder.queryParam("query", request.getKeyword());
        uriBuilder.queryParam("sort", request.getSortType());
        uriBuilder.queryParam("page", request.getPageNumber() + 1);
        uriBuilder.queryParam("size", request.getPageSize());
        URI uri = uriBuilder.build().encode().toUri();
        log.info("keyword : {}, uri : {}", request.getKeyword(), uri);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
        try {
            KakaoSearchResponse response = restTemplate.exchange(uri, HttpMethod.GET
                    , new HttpEntity<>(headers), KakaoSearchResponse.class).getBody();

            return SearchListResponse.of(
                    response.getDocumentList().stream().map(SearchApiResponse::of).collect(Collectors.toList()),
                    response.getMetaDto().getTotalCount(), request.getPageNumber(), request.getPageSize(),
                    response.getMetaDto().getIsEnd(), request.getSortType());
        } catch (HttpClientErrorException ae) {
            throw new ApiRuntimeException(HttpStatus.BAD_REQUEST, "Kakao Api 요청값 오류");
        } catch (Exception e) {
            throw new Exception("Kakao API 에러");
        }
    }

}
