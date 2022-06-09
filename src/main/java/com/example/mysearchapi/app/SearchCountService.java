package com.example.mysearchapi.app;

import com.example.mysearchapi.domain.Search;
import com.example.mysearchapi.domain.SearchRepository;
import org.springframework.stereotype.Service;

@Service
public class SearchCountService {

    private final SearchRepository repository;

    public SearchCountService(SearchRepository repository) {
        this.repository = repository;
    }

    public void saveSearchCount(String keyword) {
        Search search = repository.findSearchByKeyword(keyword)
                .orElse(Search.zero(keyword));
        search.plusCount();
        repository.save(search);
    }

}
