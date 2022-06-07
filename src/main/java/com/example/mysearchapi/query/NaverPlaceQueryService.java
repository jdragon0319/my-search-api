package com.example.mysearchapi.query;

import com.example.mysearchapi.infra.rest.ExternalApiResponse;
import com.example.mysearchapi.infra.rest.NaverClient;
import com.example.mysearchapi.infra.rest.NaverSearchRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class NaverPlaceQueryService implements ExternalSearchService {

    private final NaverClient naverClient;
    private final NaverProperties properties;

    public NaverPlaceQueryService(NaverClient naverClient, NaverProperties properties) {
        this.naverClient = naverClient;
        this.properties = properties;
    }

    public ExternalApiResponse get(String keyword) {
        NaverSearchRequest req = NaverSearchRequest.builder().query(keyword).display(5).build();
        return naverClient.getPlaces(properties.getClientId(), properties.getClientSecret(), req);
    }

    @Getter
    @Setter
    @Component
    @ConfigurationProperties("external.naver")
    static class NaverProperties {
        private String name;
        private String url;
        private String clientId;
        private String clientSecret;
    }
}
