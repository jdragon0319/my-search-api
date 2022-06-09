package com.example.mysearchapi.query;

import lombok.Builder;

public record SearchTopTenResponse(String keyword, Long count) {

    @Builder
    public SearchTopTenResponse {}

}
