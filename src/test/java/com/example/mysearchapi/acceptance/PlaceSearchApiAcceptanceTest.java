package com.example.mysearchapi.acceptance;

import com.example.mysearchapi.util.AcceptanceTest;
import com.example.mysearchapi.app.SearchPlacesResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("키워드 검색 인수테스트")
public class PlaceSearchApiAcceptanceTest extends AcceptanceTest {

    @DisplayName("키워드를 입력하면 장소 이름을 검색,정렬 후 반환한다.")
    @Test
    void 키워드로장소조회() {
        Map<String, String> params = Map.of("keyword", "인덕원 곱창");

        ExtractableResponse<Response> apiCallResult = RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .params(params)
                .when()
                .get("/extsvc/my-search-api/v1/client/search/places")
                .then().log().all()
                .extract();

        assertThat(apiCallResult.statusCode()).isEqualTo(HttpStatus.OK.value());
        SearchPlacesResponse result = apiCallResult.as(SearchPlacesResponse.class);
        assertThat(result.places()).containsExactly(
                "대명곱창", "원조곱창", "인덕원연탄불막창", "인덕원부산곱창", "한제소곱창전골인덕원점", "부산곱창", "쌍둥이곱창", "왕십리원조황소곱창");
    }
}
