package com.example.mysearchapi.app;

import lombok.Builder;

import java.util.List;

public record SearchPlacesResponse(List<String> places) {

    @Builder
    public SearchPlacesResponse {
    }

}
