package com.example.mysearchapi.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Builder
public class Places {
    private List<Place> placeList;

    public List<String> getSortedPlaceNames() {
        Set<Place> duplicatedPlaces = getDuplicatedPlaces();
        List<Place> newList = new ArrayList<>(duplicatedPlaces);

        Map<SearchApiType, List<Place>> placeBySearchType = getSearchApiTypeListMap(duplicatedPlaces);

        addKakaoPlaces(placeBySearchType, newList);
        removeAddedKakaoPlaces(placeBySearchType);

        addOtherPlaces(placeBySearchType, newList);
        return newList.stream()
                .map(place -> place.getPlaceName())
                .collect(Collectors.toList());
    }

    private void addOtherPlaces(Map<SearchApiType, List<Place>> placeBySearchType, List<Place> newList) {
        placeBySearchType.values().forEach(newList::addAll);
    }

    private Map<SearchApiType, List<Place>> getSearchApiTypeListMap(Set<Place> duplicatedPlaces) {
        return placeList.stream()
                .filter(place -> !duplicatedPlaces.contains(place))
                .collect(Collectors.groupingBy(Place::getType));
    }

    private void removeAddedKakaoPlaces(Map<SearchApiType, List<Place>> placeBySearchType) {
        placeBySearchType.remove(SearchApiType.KAKAO);
    }

    private void addKakaoPlaces(Map<SearchApiType, List<Place>> placeBySearchType, List<Place> newList) {
        List<Place> kakaoPlaces = placeBySearchType.get(SearchApiType.KAKAO);
        newList.addAll(kakaoPlaces);
    }

    private Set<Place> getDuplicatedPlaces() {
        return placeList.stream()
                .filter(place -> Collections.frequency(placeList, place) > 1)
                .collect(Collectors.toSet());
    }

    public List<String> getNames() {
        return placeList.stream()
                .map(Place::getPlaceName)
                .collect(Collectors.toList());
    }

}
