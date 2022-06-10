package com.example.mysearchapi.api;

import com.example.mysearchapi.common.logging.CollectLogging;
import com.example.mysearchapi.query.SearchTopTenQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchTopTenApi {

    private final SearchTopTenQueryService service;

    public SearchTopTenApi(SearchTopTenQueryService service) {
        this.service = service;
    }

    @CollectLogging(menu = "검색", resource = "검색어순위", method = "조회")
    @GetMapping("/extsvc/my-search-api/v1/client/search/topten")
    public ResponseEntity getTopTen() {
        return ResponseEntity.ok(service.getTopTen());
    }
}
