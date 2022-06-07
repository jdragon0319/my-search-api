package com.example.mysearchapi.domain;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface SearchRepository extends Repository<Search,Long> {
    Search save(Search firstSearch);
    Optional<Search> findSearchByKeyword(String keyword);
}
