package com.example.mysearchapi.app;

import com.example.mysearchapi.domain.Search;
import com.example.mysearchapi.domain.SearchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class SearchCountService {

    private final SearchRepository repository;

    public SearchCountService(SearchRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveSearchCount(String keyword) {
        repository.findSearchByKeyword(keyword)
                .ifPresentOrElse(
                        Search::plusCount,
                        () -> {repository.save(Search.first(keyword));}
                );

    }

    @Transactional(readOnly = true)
    public Search findSearchByKeyword(String keyword) {
        return repository.findSearchByKeyword(keyword)
                .orElseThrow( () -> new EntityNotFoundException(keyword + "가 검색된적 없습니다."));
    }

}
