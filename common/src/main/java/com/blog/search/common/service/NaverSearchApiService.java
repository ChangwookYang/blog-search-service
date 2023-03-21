package com.blog.search.common.service;

import com.blog.search.common.dto.SearchApiRequest;
import com.blog.search.common.dto.SearchApiResponse;
import com.blog.search.common.dto.SearchListResponse;
import com.blog.search.common.dto.naver.NaverSearchResponse;
import com.blog.search.common.exception.ApiRuntimeException;
import com.blog.search.common.type.ApiType;
import com.blog.search.common.type.SortType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class NaverSearchApiService implements BlogSearchApiInterface<SearchListResponse> {
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
    public SearchListResponse searchBlogDataFromApi(SearchApiRequest request) throws Exception {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(naverRestApiUrl);

        Integer start = getStart(request.getPageNumber(), request.getPageSize());
        uriBuilder.queryParam("query", request.getKeyword());
        uriBuilder.queryParam("sort", getSort(request.getSortType()));
        uriBuilder.queryParam("start", start);
        uriBuilder.queryParam("display", request.getPageSize());
        URI uri = uriBuilder.build().encode().toUri();
        log.info("keyword : {}, uri : {}", request.getKeyword(), uri);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverRestApiClientId);
        headers.set("X-Naver-Client-Secret", naverRestApiClientSecret);
        try {

            NaverSearchResponse response = restTemplate.exchange(uri, HttpMethod.GET
                    , new HttpEntity<>(headers), NaverSearchResponse.class).getBody();
            return SearchListResponse.of(
                    response.getItemList().stream().map(SearchApiResponse::of).collect(Collectors.toList()),
                    response.getTotal(), request.getPageNumber(), request.getPageSize(),
                    isEndPage(response.getTotal(), start, request.getPageSize()), request.getSortType()
            );
        } catch (HttpClientErrorException ae) {
            throw new ApiRuntimeException(ae.getStatusCode(), "Naver API 요청값 오류");
        } catch (Exception e) {
            throw new Exception("Naver API 에러");
        }
    }

    private String getSort(SortType sortType) {
        if (SortType.ACCURACY.equals(sortType))
            return "sim";
        else if (SortType.RECENCY.equals(sortType))
            return "date";
        else
            return "";
    }

    private Integer getStart(Integer pageNumber, Integer pageSize) {
        return pageNumber * pageSize + 1;
    }

    private boolean isEndPage(Long total, int start, int display) {
        return total <= start + display;
    }
}
