package com.msvc_news.msvc_news.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.msvc_news.msvc_news.utils.Constants.TOPIC_NAME;

/**
 * Configuración de tópicos Kafka.
 * <p>
 * Esta clase se encarga de declarar y crear tópicos Kafka
 * automáticamente al iniciar la aplicación.
 * <p>
 * IMPORTANTE:
 * - El topic se crea SOLO si no existe.
 * - Requiere que `spring.kafka.admin.auto-create=true`
 *   o que KafkaAdmin esté habilitado.
 */
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic newsTopic(){
        return TopicBuilder
                .name(TOPIC_NAME)
                .build();
    }
}
