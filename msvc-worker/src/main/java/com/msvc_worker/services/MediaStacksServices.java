package com.msvc_worker.services;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;

public interface MediaStacksServices {
    Mono<JsonNode> sendRequest(String date);
}
