package com.example.demo.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
public class KafkaConfig {

    public static final String SPRING_TOPIC = "spring-topic-1";
    public static final String SPRING_GROUP_ID = "spring-group-id-1";

    @Bean
    NewTopic springTopic() {
        return TopicBuilder.name(SPRING_TOPIC).build();
    }

    @Bean
    ProducerFactory<Object, Object> producerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> configs = kafkaProperties.buildAdminProperties();
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public ConsumerFactory<Object, Object> consumerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> configs = kafkaProperties.buildAdminProperties();
        Deserializer<Object> jsonDeserializer = new JsonDeserializer<>().trustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(configs, jsonDeserializer, jsonDeserializer);
    }
}
