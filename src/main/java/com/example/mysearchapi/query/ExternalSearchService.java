package com.example.mysearchapi.query;

import com.example.mysearchapi.infra.rest.ExternalApiResponse;

public interface ExternalSearchService {
    ExternalApiResponse get(String keyword);
}
