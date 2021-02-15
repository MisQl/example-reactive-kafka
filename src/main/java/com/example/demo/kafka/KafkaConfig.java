package com.example.demo.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.internals.ConsumerFactory;
import reactor.kafka.receiver.internals.DefaultKafkaReceiver;
import reactor.kafka.sender.SenderOptions;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Map;

@Configuration
public class KafkaConfig {

    public static final String SPRING_TOPIC = "spring-topic-101";
    public static final String SPRING_GROUP_ID = "spring-group-id-1";

    @Bean
    NewTopic springTopic() {
        return TopicBuilder.name(SPRING_TOPIC).build();
    }

    @Bean
    KafkaReceiver<String, String> kafkaReceiver(KafkaProperties kafkaProperties) {
        Map<String, Object> props = kafkaProperties.buildProducerProperties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, SPRING_GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        ReceiverOptions<Object, Object> receiverOptions = ReceiverOptions.create(props).subscription(Collections.singleton(SPRING_TOPIC));
        return new DefaultKafkaReceiver(ConsumerFactory.INSTANCE, receiverOptions);
    }

    @Bean
    ReactiveKafkaProducerTemplate<String, Object> reactiveKafkaProducerTemplate(ProducerFactory<String, Object> producerFactory) {
        Map<String, Object> properties = producerFactory.getConfigurationProperties();
        SenderOptions<String, Object> senderOptions = SenderOptions.<String, Object>create(properties)
                .withValueSerializer(new JsonSerializer<>())
                .closeTimeout(Duration.of(5, ChronoUnit.MINUTES))
                .producerProperty(ProducerConfig.RETRIES_CONFIG, 10);

        return new ReactiveKafkaProducerTemplate<>(senderOptions);
    }
}
