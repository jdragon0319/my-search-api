package com.example.mysearchapi.domain;

import java.util.List;

public class PlaceTestDatas {
    private static final Place place1 = new Place("원조곱창", SearchApiType.KAKAO);
    private static final Place place2 = new Place("인덕원연탄불막창", SearchApiType.KAKAO);
    private static final Place place3 = new Place("대명곱창", SearchApiType.KAKAO);
    private static final Place place4 = new Place("인덕원부산곱창", SearchApiType.KAKAO);
    private static final Place place5 = new Place("황제소곱창전골인덕원점", SearchApiType.KAKAO);
    private static final Place place6 = new Place("원조곱창", SearchApiType.NAVER);
    private static final Place place7 = new Place("부산곱창", SearchApiType.NAVER);
    private static final Place place8 = new Place("쌍둥이곱창", SearchApiType.NAVER);
    private static final Place place9 = new Place("왕십리원조황소곱창", SearchApiType.NAVER);
    private static final Place place10 = new Place("대명곱창", SearchApiType.NAVER);

    public static List<Place> getPlaces() {
        return List.of(
                place1, place2, place3, place4, place5, place6, place7, place8, place9, place10
        );
    }

}
