package com.example.mysearchapi.acceptance;

import com.example.mysearchapi.util.AcceptanceTest;
import com.example.mysearchapi.util.SearchRepositoryHelper;
import com.example.mysearchapi.domain.SearchTestDatas;
import com.example.mysearchapi.query.SearchTopTenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TOP 10 인수테스트")
public class SearchTopTenApiAcceptanceTest extends AcceptanceTest {

    @Autowired
    private SearchRepositoryHelper helper;

    @BeforeEach
    public void setUp() {
        super.setUp();
        helper.saveTestData(SearchTestDatas.getSearchTestDatas());
    }

    @DisplayName("최다 검색, 최대 10개의 검색 목록을 반환한다.")
    @Test
    void 최대10개의검색목록조회() {
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
