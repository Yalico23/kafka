package com.msvc_worker.repository;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;

public interface NewsRepository {
    Mono<Boolean> saveNews(String date, JsonNode newsData);
}
