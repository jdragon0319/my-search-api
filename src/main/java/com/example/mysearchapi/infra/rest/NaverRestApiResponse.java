package com.example.mysearchapi.infra.rest;

import com.example.mysearchapi.domain.Place;
import com.example.mysearchapi.domain.SearchApiType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
import java.util.stream.Collectors;

@JsonDeserialize(as = NaverRestApiResponse.class)
public record NaverRestApiResponse(List<NaverRestApiResponseItem> items) implements ExternalApiResponse {

    @Override
    public List<Place> getApiResults() {
        return items.stream()
                .map(item -> Place.builder()
                        .type(SearchApiType.NAVER)
                        .placeName(convert(item.title()))
                        //.address(item.address())
                        .build())
                .collect(Collectors.toList());
    }

    private String convert(String placeName) {
        return placeName.trim()
                .replace(" ", "")
                .replace("<b>", "")
                .replace("</b>", "");
    }
}
