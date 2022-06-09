package com.example.mysearchapi.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SearchRepositoryTest {

    @Autowired
    private SearchRepository searchRepository;

    @BeforeEach
    void setUp() {
        for (Search search : SearchTestDatas.getSearchTestDatas()) {
            searchRepository.save(search);
        }
    }

    @DisplayName("키워드로 조회")
    @Test
    void 키워드조회() {
        Search search = searchRepository.findSearchByKeyword("곱창").get();
        assertThat(search.getId()).isEqualTo(1L);
        assertThat(search.getKeyword()).isEqualTo("곱창");
    }

    @DisplayName("상위 10개 검색어 조회")
    @Test
    void 상위10개검색어조회() {
        List<Search> result = searchRepository.findTop10ByOrderByCountDesc();
        assertThat(result.size()).isEqualTo(10);
    }


}
