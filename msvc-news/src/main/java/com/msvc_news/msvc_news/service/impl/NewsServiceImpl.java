package com.msvc_news.msvc_news.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.msvc_news.msvc_news.repository.NewsRepository;
import com.msvc_news.msvc_news.service.NewsService;
import static com.msvc_news.msvc_news.utils.Constants.*;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final NewsRepository newsRepository;

    // Deja un mensaje en el topic Kafka con la fecha proporcionada
    @Override
    public Mono<Void> publishToMessageBroker(String date) {
        // Registro que se va a enviar al topic Kafka
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_NAME, null, date);
        return Mono
                .fromFuture(kafkaTemplate.send(producerRecord)) // envía el registro al topic Kafka
                .then(); // ignora el resultado y devuelve Mono<Void>, espera a que la operación se complete
    }

    @Override
    public Mono<JsonNode> getNews(String date) {
        return newsRepository.getNews(date)
                .switchIfEmpty(triggerFetchAndReturnEmpty(date));
    }

    // Al usar Mono es lazy evaluation, este método solo se ejecuta si no hay datos en Redis
    private Mono<JsonNode> triggerFetchAndReturnEmpty(String date) {
        return publishToMessageBroker(date)
                .then(Mono.empty());
    }
}
