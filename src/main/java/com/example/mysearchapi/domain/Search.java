package com.example.mysearchapi.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String keyword;
    private Long count;

    protected Search() {
    }

    @Builder
    public Search(Long id, String keyword, Long count) {
        this.id = id;
        this.keyword = keyword;
        this.count = count;
    }

    @Builder
    public Search(String keyword, Long count) {
        this.keyword = keyword;
        this.count = count;
    }

    public static Search first(String keyword) {
        return new Search(keyword, 1L);
    }

    public void plusCount() {
        this.count++;
    }

}
