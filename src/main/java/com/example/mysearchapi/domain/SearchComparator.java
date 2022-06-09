package com.example.mysearchapi.domain;

import java.util.Comparator;

public class SearchComparator implements Comparator<Search> {

    @Override
    public int compare(Search s1, Search s2) {
        if (s1.getCount() - s2.getCount() > 0) {
            return -1;
        } else if (s1.getCount() - s2.getCount() < 0) {
            return 1;
        } else {
            return s1.getKeyword().compareTo(s2.getKeyword());
        }
    }

}
