package com.example.mysearchapi.app;

import lombok.Builder;

public record SearchCountResponse(String keyword, Long count) {

    @Builder
    public SearchCountResponse{

    }
}
