package com.example.mysearchapi.infra.rest;

import java.util.Locale;

public record NaverRestApiResponseItem (
        String title, String telephone, String address, String roadAddress, String description) {
}
