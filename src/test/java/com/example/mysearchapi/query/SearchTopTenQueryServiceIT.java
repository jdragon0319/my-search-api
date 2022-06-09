package com.example.mysearchapi.query;

import com.example.mysearchapi.domain.Search;
import com.example.mysearchapi.domain.SearchRepository;
import com.example.mysearchapi.domain.SearchTestDatas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SearchTopTenQueryService 통합테스트")
@SpringBootTest(properties = "spring.profiles.active:test")
class SearchTopTenQueryServiceIT {

    @Autowired
    private SearchTopTenQueryService service;

    @Autowired
    private SearchRepository searchRepository;

    @BeforeEach
    void setUp() {
        for (Search search : SearchTestDatas.getSearchTestDatas()) {
            searchRepository.save(search);
        }
    }

    @DisplayName("상위 10개 검색어 정렬 후 조회")
    @Test
    void test() {
        List<SearchTopTenResponse> result = service.getTopTen();

        assertThat(result.stream().map(SearchTopTenResponse::keyword).collect(Collectors.toList()))
                .containsExactly("소고기", "된장찌개", "닭갈비", "떡볶이", "삼겹살", "하몽", "참치", "김치찌개", "커피", "치즈");
    }

}
