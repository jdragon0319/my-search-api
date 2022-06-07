package com.example.mysearchapi.query;

import com.example.mysearchapi.domain.Places;
import com.example.mysearchapi.infra.rest.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;


class PlaceQueryServiceTest {

    private ExternalSearchService searchServices1 = spy(ExternalSearchService.class);
    private ExternalSearchService searchServices2 = spy(ExternalSearchService.class);
    private Set<ExternalSearchService> set = Set.of(searchServices1, searchServices2);
    private PlaceQueryService placeQueryService = new PlaceQueryService(set);
    private NaverRestApiResponse naverRestApiResponse;
    private KakaoRestApiResponse kakaoRestApiResponse;


    @BeforeEach
    void setUp() {
        kakaoRestApiResponse = new KakaoRestApiResponse(
                List.of(
                        new KakaoRestApiResponseItem("원조곱창", "12345", "주소1", "주소1"),
                        new KakaoRestApiResponseItem("황제곱창", "12345", "주소2", "주소2"),
                        new KakaoRestApiResponseItem("굿굿곱창", "12345", "주소3", "주소3")
                )
        );
        naverRestApiResponse = new NaverRestApiResponse(
                List.of(
                        new NaverRestApiResponseItem("원조곱창", "12345", "주소1", "주소1", "설명1"),
                        new NaverRestApiResponseItem("황제곱창", "12345", "주소2", "주소2", "설명2"),
                        new NaverRestApiResponseItem("인생곱창", "12345", "주소3", "주소3", "설명3")
                )
        );
        BDDMockito.given(searchServices1.get(anyString())).willReturn(kakaoRestApiResponse);
        BDDMockito.given(searchServices2.get(anyString())).willReturn(naverRestApiResponse);
    }

    @DisplayName("Service 에서 한번씩 메서드 호출")
    @Test
    void 여러서비스에서조회() {
        placeQueryService.getPlace("인덕원 곱창");
        for (ExternalSearchService service : set) {
            BDDMockito.then(service).should(times(1)).get(anyString());
        }
    }

    @DisplayName("검색결과 정렬 후 반환")
    @Test
    void 검색결과정렬() {
        Places places = placeQueryService.getPlace("인덕원 곱창");
        assertThat(places.getSortedPlaceNames()).containsExactly("황제곱창","원조곱창","굿굿곱창","인생곱창");
    }

}
