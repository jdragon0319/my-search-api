package com.example.mysearchapi.query;

import com.example.mysearchapi.domain.Search;
import com.example.mysearchapi.domain.SearchComparator;
import com.example.mysearchapi.domain.SearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchTopTenQueryService {

    private final SearchRepository searchRepository;

    public SearchTopTenQueryService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public List<SearchTopTenResponse> getTopTen() {
        return searchRepository.findTop10ByOrderByCountDesc()
                .stream()
                .sorted(new SearchComparator())
                .map(search -> SearchTopTenResponse.builder()
                        .keyword(search.getKeyword())
                        .count(search.getCount())
                        .build())
                .collect(Collectors.toList());
    }
}
