package com.example.mysearchapi.infra.rest;

import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

@Headers(value = {
        "X-Naver-Client-Id: {client-id}", "X-Naver-Client-Secret: {client-secret}",
        "Accept: application/json", "Content-Type: application/json"
})
public interface NaverClient {

    @RequestLine("GET /v1/search/local.json")
    ExternalApiResponse getPlaces(
            @Param("client-id") String clientId,
            @Param("client-secret") String clientSecret,
            @QueryMap NaverSearchRequest request);
}
