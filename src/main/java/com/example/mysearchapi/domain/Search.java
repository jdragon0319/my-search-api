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
    public Search(String keyword, Long count) {
        this.keyword = keyword;
        this.count = count;
    }

    public static Search zero(String keyword) {
        return new Search(keyword, 0L);
    }

    public void plusCount() {
        this.count++;
    }
}
