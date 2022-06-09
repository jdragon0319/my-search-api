package com.example.mysearchapi.infra.rest;

import com.example.mysearchapi.domain.Place;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("외부api 호출 테스트")
@SpringBootTest(properties = "spring.profiles.active:test")
public class ExternalApiClientTest {

    @Autowired
    private KakaoClient kakaoClient;
    @Autowired
    private NaverClient naverClient;

    @DisplayName("외부 API 호출 테스트")
    @Test
    void apiCallTest() {
        KakaoSearchRequest req = KakaoSearchRequest.builder().query("인덕원 곱창").size(10).build();
        ExternalApiResponse kakaoApiResult = kakaoClient.getPlaces("KakaoAK ce6205bab96ed93dc54f763fe29c8c99", req);
        List<Place> items1 = kakaoApiResult.getApiResults();
        items1.forEach(item -> System.out.println(item));
        assertThat(items1.size()).isEqualTo(10);

        System.out.println("=======================================================================");

        NaverSearchRequest req2 = NaverSearchRequest.builder().query("인덕원 곱창").display(5).build();
        ExternalApiResponse naverApiResult = naverClient.getPlaces("eyb_oh093LKBMiuojnVg", "9eyHXV1IDi", req2);
        List<Place> items2 = naverApiResult.getApiResults();
        items2.forEach(item -> System.out.println(item));
        assertThat(items2.size()).isEqualTo(5);
    }
}
