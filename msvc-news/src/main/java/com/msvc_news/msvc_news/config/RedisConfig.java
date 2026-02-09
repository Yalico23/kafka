package com.msvc_news.msvc_news.config;

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

    /**
     * Host de Redis (ej: localhost, redis, redis.amazonaws.com)
     */
    @Value("${spring.data.redis.host}")
    private String host;

    /**
     * Puerto de Redis (por defecto 6379)
     */
    @Value("${spring.data.redis.port}")
    private String port;

    /**
     * Password de Redis (opcional en entornos locales)
     */
    @Value("${spring.data.redis.password}")
    private String password;


    /**
     * Bean que configura la conexión reactiva hacia Redis.
     * <p>
     * ✔ Usa Redis en modo standalone
     * ✔ Implementa Lettuce (cliente no bloqueante)
     * ✔ Compatible con Spring WebFlux
     * <p>
     * Este bean es usado internamente por ReactiveRedisTemplate.
     */
    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        // Validamos que las propiedades no sean nulas
        redisStandaloneConfiguration.setHostName(Objects.requireNonNull(host));
        redisStandaloneConfiguration.setPort(Integer.parseInt(Objects.requireNonNull(port)));
        // Password obligatorio según configuración
        redisStandaloneConfiguration.setPassword(Objects.requireNonNull(password));
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    /**
     * Bean principal para interactuar con Redis de forma reactiva.
     * <p>
     * ReactiveRedisOperations permite:
     * - Operaciones no bloqueantes (Mono / Flux)
     * - Manejo de Strings, Hashes, Sets, Lists
     * - Integración natural con WebFlux
     * <p>
     * Serialización:
     * - Claves: String
     * - Valores: JSON (Jackson)
     */
    @Bean
    public ReactiveRedisOperations<String, JsonNode> reactiveRedisOperations
            (ReactiveRedisConnectionFactory reactiveRedisConnectionFactory){

        // Serializer JSON para valores
        Jackson2JsonRedisSerializer<JsonNode> serializer = new Jackson2JsonRedisSerializer<>(JsonNode.class);

        // Builder del contexto de serialización
        RedisSerializationContext.RedisSerializationContextBuilder<String,JsonNode> builder =
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
