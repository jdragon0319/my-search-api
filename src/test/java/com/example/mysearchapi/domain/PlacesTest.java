package com.example.mysearchapi.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PlacesTest {

    @DisplayName("정렬된 장소 이름 리스트를 반환")
    @Test
    void 정렬된장소이름() {
        Places places = new Places(List.of(
                new Place("원조곱창", SearchApiType.KAKAO),
                new Place("인덕원연탄불막창", SearchApiType.KAKAO),
                new Place("대명곱창", SearchApiType.KAKAO),
                new Place("인덕원부산곱창", SearchApiType.KAKAO),
                new Place("황제소곱창전골인덕원점", SearchApiType.KAKAO),
                new Place("원조곱창", SearchApiType.NAVER),
                new Place("부산곱창", SearchApiType.NAVER),
                new Place("쌍둥이곱창", SearchApiType.NAVER),
                new Place("왕십리원조황소곱창", SearchApiType.NAVER),
                new Place("대명곱창", SearchApiType.NAVER)
        ));

        List<String> result = places.getSortedPlaceNames();

        assertThat(result).containsExactly(
                "대명곱창", "원조곱창", "인덕원연탄불막창", "인덕원부산곱창", "황제소곱창전골인덕원점", "부산곱창", "쌍둥이곱창", "왕십리원조황소곱창"
        );
    }

}
