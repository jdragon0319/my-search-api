package com.example.mysearchapi.api;

import com.example.mysearchapi.app.SearchPlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceSearchApi {

    private final SearchPlaceService searchPlaceService;

    public PlaceSearchApi(SearchPlaceService searchPlaceService) {
        this.searchPlaceService = searchPlaceService;
    }

    @GetMapping("/extsvc/my-search-api/v1/client/search/places")
    public ResponseEntity getPlaces(String keyword) {
        return ResponseEntity.ok(searchPlaceService.searchPlaces(keyword));
    }

}
