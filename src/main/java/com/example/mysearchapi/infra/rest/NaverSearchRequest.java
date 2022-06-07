package com.example.mysearchapi.infra.rest;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NaverSearchRequest {
    private String query;
    private int display;
}


/*
query	string	Y	-	검색을 원하는 문자열로서 UTF-8로 인코딩한다.
display	integer	N	1(기본값), 5(최대)	검색 결과 출력 건수 지정
start	integer	N	1(기본값), 1(최대)	검색 시작 위치로 1만 가능
sort	string	N	random (기본값), comment	정렬 옵션: random(유사도순), comment(카페/블로그 리뷰 개수 순)
 */
