package com.example.mysearchapi.domain;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface SearchRepository extends Repository<Search, Long> {
    Search save(Search firstSearch);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = "javax.persistence.lock.timeout", value = "1000"))
    Optional<Search> findSearchByKeyword(String keyword);

    List<Search> findTop10ByOrderByCountDesc();
}
