package com.msvc_news.msvc_news.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String TOPIC_NAME = "news";
    public static final String DATE_FORMAT = "^\\d{4}-\\d{2}-\\d{2}$"; // Formato YYYY-MM-DD
    public static final String DATE_NOT_BLANK_MESSAGE = "Date request param cannot be empty or null";
    public static final String DATE_PATTERN_MESSAGE = "Date request param must match the pattern YYYY-MM-DD";
    public static final String DATA_FOUND_MESSAGE = "News found for the given date";
    public static final String DATA_NOT_FOUND_MESSAGE = "No news found for the given date";
}

