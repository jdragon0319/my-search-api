package com.example.mysearchapi.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Place {
    private String placeName;
    private SearchApiType type;

    @Builder
    public Place(String placeName, SearchApiType type) {
        this.placeName = placeName;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return placeName.equals(place.placeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeName);
    }
}
