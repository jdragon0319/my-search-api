package com.example.mysearchapi.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table
public class Search implements Comparable {
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

    public static Search zero(String keyword) {
        return new Search(keyword, 0L);
    }

    public void plusCount() {
        this.count++;
    }

    @Override
    public int compareTo(Object o) {
        Search other = (Search) o;
        if (this.count - other.count > 0) {
            return -1;
        } else if (this.count - other.count < 0) {
            return 1;
        } else {
            return this.keyword.compareTo(other.keyword);
        }
    }
}
