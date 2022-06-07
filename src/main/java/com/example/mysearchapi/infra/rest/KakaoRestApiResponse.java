package com.example.mysearchapi.infra.rest;

import com.example.mysearchapi.domain.Place;
import com.example.mysearchapi.domain.SearchApiType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
import java.util.stream.Collectors;


@JsonDeserialize(as = KakaoRestApiResponse.class)
public record KakaoRestApiResponse(List<KakaoRestApiResponseItem> documents) implements ExternalApiResponse {

    @Override
    public List<Place> getApiResults() {
        return documents.stream()
                .map(document -> Place.builder()
                        .type(SearchApiType.KAKAO)
                        .placeName(convert(document.placeName()))
                        //.address(document.addressName())
                        .build())
                .collect(Collectors.toList());
    }

    private String convert(String placeName) {
        return placeName.trim()
                .replace(" ", "");
    }
}
