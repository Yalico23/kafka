package com.msvc_worker.config;


import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Objects;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private String port;

    @Value("${spring.data.redis.password}")
    private String password;

    // ReactiveRedisConnectionFactory, Configuramos el modo reactivo con webflux y lettuce y no Jedis(bloquente)
    // RedisStandaloneConfiguration, Aquí defines explícitamente el modo
    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        // Validamos que las propiedades no sean nulas
        redisStandaloneConfiguration.setHostName(Objects.requireNonNull(host));
        redisStandaloneConfiguration.setPort(Integer.parseInt(Objects.requireNonNull(port)));
        // Password obligatorio según configuración
        redisStandaloneConfiguration.setPassword(Objects.requireNonNull(password));
        // No bloqueante, No bloqueante y Recomendado para WebFlux
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    // Se configura la serialización porque Redis entiende por bytes no por Java Objects
    @Bean
    public ReactiveRedisOperations<String, JsonNode> reactiveRedisOperations
    (ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {

        // Serializer JSON para valores
        Jackson2JsonRedisSerializer<JsonNode> serializer = new Jackson2JsonRedisSerializer<>(JsonNode.class);

        // Builder del contexto de serialización
        RedisSerializationContext.RedisSerializationContextBuilder<String, JsonNode> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        // Configuración completa de serialización
        RedisSerializationContext<String, JsonNode> context = builder
                .value(serializer)      // Valores normales
                .hashKey(serializer)    // Claves en estructuras Hash
                .hashValue(serializer)  // Valores en estructuras Hash
                .build();

        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, context);
    }
}
