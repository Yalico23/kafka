package com.msvc_news.msvc_news.service;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;

public interface NewsService {

    Mono<Void> publishToMessageBroker(String date);
    Mono<JsonNode> getNews (String date);
}
