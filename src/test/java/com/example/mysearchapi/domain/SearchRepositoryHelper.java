package com.example.mysearchapi.domain;

import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@Component
@ActiveProfiles("test")
public class SearchRepositoryHelper {

    private final SearchRepository searchRepository;

    public SearchRepositoryHelper(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public void saveTestData(List<Search> datas) {
        for (Search search : datas) {
            searchRepository.save(search);
        }
    }
}
