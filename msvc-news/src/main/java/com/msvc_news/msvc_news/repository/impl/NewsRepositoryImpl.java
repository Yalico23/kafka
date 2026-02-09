package com.msvc_news.msvc_news.repository.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.msvc_news.msvc_news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class NewsRepositoryImpl implements NewsRepository {

    // Esta instancia es inyectada automáticamente por Spring previamente modificada en RedisConfig
    private final ReactiveRedisOperations<String, JsonNode> reactiveRedisOperations;

    @Override
    public Mono<JsonNode> getNews(String date) {
        // Te devuelve un handler especializado para operar sobre valores tipo STRING en Redis
        // Obtiene el valor asociado a la clave 'date'
        return reactiveRedisOperations.opsForValue().get(date);
    }
}
