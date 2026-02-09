package com.msvc_worker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${mediastack.uri}")
    private String baseUrl = "http://msvc-news";

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl(baseUrl)
                .build();
    }

}
