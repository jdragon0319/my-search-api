package com.example.mysearchapi.api;

import com.example.mysearchapi.app.SearchPlaceService;
import com.example.mysearchapi.common.logging.CollectLogging;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceSearchApi {

    private final SearchPlaceService searchPlaceService;

    public PlaceSearchApi(SearchPlaceService searchPlaceService) {
        this.searchPlaceService = searchPlaceService;
    }

    @CollectLogging(menu = "검색", resource = "검색장소목록", method = "조회")
    @GetMapping("/extsvc/my-search-api/v1/client/search/places")
    public ResponseEntity getPlaces(String keyword) {
        return ResponseEntity.ok(searchPlaceService.searchPlaces(keyword));
    }

}
