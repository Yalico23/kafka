package com.msvc_worker.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.msvc_worker.models.exception.ExternalException;
import com.msvc_worker.services.MediaStacksServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MediaStackServicesImpl implements MediaStacksServices {

    @Value("${mediastack.access_key}")
    private String apikey;

    @Value("${mediastack.country}")
    private String country;

    @Value("${mediastack.limit}")
    private String limits;

    private final WebClient webClient;

    @Override
    public Mono<JsonNode> sendRequest(String date) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        //.path("/v1/news") ya esta definido en el WebClient Builder
                        .queryParam("access_key", apikey)
                        .queryParam("countries", country)
                        .queryParam("date", date)
                        .queryParam("limit", limits)
                        .build())
                .retrieve()// Realiza la solicitud y obtiene la respuesta
                .onStatus(HttpStatusCode::isError, response ->
                        // manejo de errores
                    response.bodyToMono(String.class)
                            .flatMap(body -> {
                                log.error("Error with external Api - Status: {}, Body: {}", response.statusCode(), body);
                                return  Mono.error(new ExternalException((HttpStatus) response.statusCode(), body));
                            }))
                .bodyToMono(JsonNode.class);
        // Usar JsonNode para manejar respuestas JSON dinámicas de apis externas que pueden cambiar
    }
}
