package com.example.mysearchapi.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SearchRepositoryTest {

    @Autowired
    private SearchRepository searchRepository;

    @Test
    void test() {
        Search search1 = Search.builder().keyword("곱창").count(1L).build();
        Search search2 = Search.builder().keyword("김치").count(5L).build();
        Search search3 = Search.builder().keyword("김밥").count(10L).build();
        Search search4 = Search.builder().keyword("삼겹살").count(25L).build();
        Search search5 = Search.builder().keyword("치즈").count(15L).build();
        Search search6 = Search.builder().keyword("참치").count(22L).build();
        Search search7 = Search.builder().keyword("떡볶이").count(25L).build();
        Search search8 = Search.builder().keyword("된장찌개").count(30L).build();
        Search search9 = Search.builder().keyword("김치찌개").count(20L).build();
        Search search10 = Search.builder().keyword("소고기").count(50L).build();
        Search search11 = Search.builder().keyword("커피").count(17L).build();
        Search search12 = Search.builder().keyword("환타").count(13L).build();
        Search search13 = Search.builder().keyword("콜라").count(7L).build();
        Search search14 = Search.builder().keyword("닭갈비").count(25L).build();
        Search search15 = Search.builder().keyword("하몽").count(25L).build();

        searchRepository.save(search1);
        searchRepository.save(search2);
        searchRepository.save(search3);
        searchRepository.save(search4);
        searchRepository.save(search5);
        searchRepository.save(search6);
        searchRepository.save(search7);
        searchRepository.save(search8);
        searchRepository.save(search9);
        searchRepository.save(search10);
        searchRepository.save(search11);
        searchRepository.save(search12);
        searchRepository.save(search13);
        searchRepository.save(search14);
        searchRepository.save(search15);

        List<Search> result = searchRepository.findTop10ByOrderByCountDesc();
        Collections.sort(result);

        assertThat(result.stream().map(Search::getKeyword).collect(Collectors.toList()))
                .containsExactly("소고기", "된장찌개", "닭갈비", "떡볶이", "삼겹살", "하몽", "참치", "김치찌개", "커피", "치즈");
    }

}
