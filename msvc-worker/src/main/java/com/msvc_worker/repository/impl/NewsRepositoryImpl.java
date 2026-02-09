package com.msvc_worker.repository.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.msvc_worker.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class NewsRepositoryImpl implements NewsRepository {

    private final ReactiveRedisOperations<String, JsonNode> redis;

    // Convierte un objeto Java en JSON y lo guarda en Redis como caché por 1 hora, de forma reactiva y segura.
    @Override
    public Mono<Boolean> saveNews(String date, JsonNode newsData) {
        Duration ttl = Duration.ofHours(1L);
        return redis.opsForValue()
                .set(date, newsData, ttl);
        // key, value, time to live
    }
}
