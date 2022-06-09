package com.example.mysearchapi.domain;

import java.util.List;

public class SearchTestDatas {
    private static final Search search1 = Search.builder().id(1L).keyword("곱창").count(1L).build();
    private static final Search search2 = Search.builder().id(2L).keyword("김치").count(5L).build();
    private static final Search search3 = Search.builder().id(3L).keyword("김밥").count(10L).build();
    private static final Search search4 = Search.builder().id(4L).keyword("삼겹살").count(25L).build();
    private static final Search search5 = Search.builder().id(5L).keyword("치즈").count(15L).build();
    private static final Search search6 = Search.builder().id(6L).keyword("참치").count(22L).build();
    private static final Search search7 = Search.builder().id(7L).keyword("떡볶이").count(25L).build();
    private static final Search search8 = Search.builder().id(8L).keyword("된장찌개").count(30L).build();
    private static final Search search9 = Search.builder().id(9L).keyword("김치찌개").count(20L).build();
    private static final Search search10 = Search.builder().id(10L).keyword("소고기").count(50L).build();
    private static final Search search11 = Search.builder().id(11L).keyword("커피").count(17L).build();
    private static final Search search12 = Search.builder().id(12L).keyword("환타").count(13L).build();
    private static final Search search13 = Search.builder().id(13L).keyword("콜라").count(7L).build();
    private static final Search search14 = Search.builder().id(14L).keyword("닭갈비").count(25L).build();
    private static final Search search15 = Search.builder().id(15L).keyword("하몽").count(25L).build();

    public static List<Search> getSearchTestDatas() {
        return List.of(
                search1, search2, search3, search4, search5, search6, search7, search8, search9, search10,
                search11, search12, search13, search14, search15
        );
    }

}
