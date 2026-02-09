package com.msvc_worker.listener;

import com.msvc_worker.models.exception.ExternalException;
import com.msvc_worker.repository.NewsRepository;
import com.msvc_worker.services.MediaStacksServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.msvc_worker.utils.Constans.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaListeners {

    private final MediaStacksServices mediaStacksServices;
    private final NewsRepository newsRepository;

    @KafkaListener(topics = TOPIC_NAME, groupId = MESSAGE_GROUP_NAME)
    public void listener(String date){
        log.info("Listener received date: {}", date);
        mediaStacksServices.sendRequest(date)
                .flatMap(jsonNode -> newsRepository.saveNews(date, jsonNode))
                .doOnNext(saved -> {
                    if(saved){
                        log.info("News data for date {} saved successfully in Redis.", date);
                    } else {
                        log.warn("Failed to save news data for date {} in Redis.", date);
                    }
                })
                .doOnError(error -> {
                    if(error instanceof ExternalException apiException){
                        log.error("Failure in external Api - Status: {}, body: {}", apiException.getStatus(), apiException.getResponseBody());
                    }else{
                        log.error("Error processing Kafka message: {}", error.getMessage(), error);
                    }
                })
                .subscribe();
    }

}
