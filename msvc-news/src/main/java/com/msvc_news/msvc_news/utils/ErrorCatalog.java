package com.msvc_news.msvc_news.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCatalog {

    INVALID_PARAMETER("ERR001", "The provided parameter is invalid."),
    INTERNAL_SERVER_ERROR("ERR002", "An internal server error occurred.");

    private final String code;
    private final String message;

}
