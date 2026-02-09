package com.msvc_news.msvc_news.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuración del Producer de Kafka.
 * <p>
 * Esta clase define:
 * - Las propiedades del productor Kafka
 * - La fábrica de productores
 * - El KafkaTemplate usado por los servicios
 * <p>
 * Es responsable de:
 * - Enviar eventos a Kafka
 * - Garantizar confiabilidad y orden
 * - Controlar performance y reintentos
 */
@Configuration
public class KafkaProducerConfig {

    /**
     * Dirección del cluster Kafka
     * Ejemplo: localhost:9092
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Propiedades base del Producer Kafka.
     * <p>
     * Estas propiedades determinan:
     * - A qué cluster conectarse
     * - Cómo serializar los mensajes
     */
    @Bean
    public Map<String, Object> producerConfigs(){
        HashMap<String, Object> props = new HashMap<>();
        // Dirección de los brokers Kafka
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // Serialización de la clave (String)
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // Serialización del valor (String)
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    // Fábrica de Productores Kafka
    @Bean
    public ProducerFactory<String,String> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    // Es el entry point oficial para publicar eventos a Kafka
    @Bean
    public KafkaTemplate<String,String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

}
