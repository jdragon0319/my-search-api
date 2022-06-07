package com.example.mysearchapi.infra.rest;

import com.example.mysearchapi.domain.Place;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Collections;
import java.util.List;

@JsonDeserialize(using = ExternalApiResponseDeserializer.class)
public interface ExternalApiResponse {
    List<Place> getApiResults();

    static KakaoRestApiResponse defaultResponse() {
        return new KakaoRestApiResponse(Collections.emptyList());
    }
}
