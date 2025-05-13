package com.ufro.voy_y_vuelvo.controller;

import lombok.Getter;

@Getter
public enum ApiURL {
    API_URL("http://localhost:8080");

    private final String url;

    ApiURL(String url) {
        this.url = url;
    }
}
