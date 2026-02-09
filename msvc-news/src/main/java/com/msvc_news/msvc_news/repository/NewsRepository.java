package com.msvc_news.msvc_news.repository;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;

public interface NewsRepository {

    Mono<JsonNode> getNews(String date);
}
