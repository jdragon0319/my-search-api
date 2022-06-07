package com.example.mysearchapi.acceptance;

import com.example.mysearchapi.AcceptanceTest;
import com.example.mysearchapi.app.SearchTopTenResponse;
import com.example.mysearchapi.domain.Search;
import com.example.mysearchapi.domain.SearchRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TOP 10 테스트")
public class SearchTopTenApiAcceptanceTest extends AcceptanceTest {

    @Autowired
    private SearchRepository searchRepository;

    @DisplayName("최다 검색, 최대 10개의 검색 목록을 반환한다.")
    @Test
    void 최대10개의검색목록조회() {
        // 데이터 준비
        // 곱창 1, 김치 5, 김밥 10, 삼겹살 27, 치즈 20, 참치 22, 떡볶이 25, 된장찌개 30, 김치찌개 20, 소고기 50, 커피 20

        ExtractableResponse<Response> apiResponse = RestAssured
                .given().log().all()
                .when()
                .get("/extsvc/my-search-api/v1/client/search/topten")
                .then().log().all()
                .extract();

        List<SearchTopTenResponse> list = apiResponse.jsonPath().getList(".", SearchTopTenResponse.class);
        assertThat(list.size()).isEqualTo(10);
    }

}
