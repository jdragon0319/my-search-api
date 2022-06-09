package com.example.mysearchapi.app;

import com.example.mysearchapi.domain.Places;
import com.example.mysearchapi.query.PlaceQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SearchPlaceService {

    private final PlaceQueryService placeQueryService;
    private final SearchCountService searchCountService;


    public SearchPlaceService(PlaceQueryService placeQueryService, SearchCountService searchCountService) {
        this.placeQueryService = placeQueryService;
        this.searchCountService = searchCountService;
    }

    @Transactional
    public SearchPlacesResponse searchPlaces(String keyword) {
        searchCountService.saveSearchCount(keyword);
        Places places = placeQueryService.getPlace(keyword);
        List<String> sortedPlaces = places.getSortedPlaceNames();
        return SearchPlacesResponse.builder()
                .places(sortedPlaces)
                .build();
    }

}
