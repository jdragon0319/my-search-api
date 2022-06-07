package com.example.mysearchapi.query;

import com.example.mysearchapi.infra.rest.ExternalApiResponse;
import com.example.mysearchapi.infra.rest.KakaoClient;
import com.example.mysearchapi.infra.rest.KakaoSearchRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class KakaoPlaceQueryService implements ExternalSearchService {

    private final KakaoClient kakaoClient;
    private final KakaoProperties properties;

    public KakaoPlaceQueryService(KakaoClient kakaoClient, KakaoProperties properties) {
        this.kakaoClient = kakaoClient;
        this.properties = properties;
    }

    public ExternalApiResponse get(String keyword) {
        KakaoSearchRequest req = KakaoSearchRequest.builder().query(keyword).size(5).build();
        return kakaoClient.getPlaces(properties.getKey(), req);
    }

    @Getter
    @Setter
    @Component
    @ConfigurationProperties("external.kakao")
    static class KakaoProperties {
        private String name;
        private String url;
        private String key;
    }
}
