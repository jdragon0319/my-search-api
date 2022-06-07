package com.example.mysearchapi.query;

import com.example.mysearchapi.infra.rest.ExternalApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;


class PlaceQueryServiceTest {

    private ExternalSearchService searchServices1 = spy(ExternalSearchService.class);
    private ExternalSearchService searchServices2 = spy(ExternalSearchService.class);
    private Set<ExternalSearchService> set = Set.of(searchServices1, searchServices2);
    private PlaceQueryService placeQueryService = new PlaceQueryService(set);

    @DisplayName("여러 검색API 에서 장소 조회")
    @Test
    void searchPlacesFromMultipleApi() {
        BDDMockito.given(searchServices1.get(anyString())).willReturn(ExternalApiResponse.defaultResponse());
        BDDMockito.given(searchServices2.get(anyString())).willReturn(ExternalApiResponse.defaultResponse());

        placeQueryService.getPlace("인덕원 곱창");

        for (ExternalSearchService service : set) {
            BDDMockito.then(service).should(times(1)).get(anyString());
        }
    }

}
