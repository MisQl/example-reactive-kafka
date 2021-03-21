package com.example.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.example.demo.kafka.KafkaConfig.SPRING_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaPublisher implements CommandLineRunner {

    private final ReactiveKafkaProducerTemplate<String, Object> kafkaProducerTemplate;

    @Override
    public void run(String... args) {
        Flux.interval(Duration.ofSeconds(5))
                .map(i -> new KafkaMessage1(getTime()))
                .map(this::buildRecord)
                .flatMap(kafkaProducerTemplate::send)
                .subscribe();

        Flux.interval(Duration.ofSeconds(30))
                .map(i -> new KafkaMessage2(getTime()))
                .map(this::buildRecord)
                .flatMap(kafkaProducerTemplate::send)
                .subscribe();
    }

    private ProducerRecord<String, Object> buildRecord(Object object) {
        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(SPRING_TOPIC, object);
        producerRecord.headers().add("token", "token-value".getBytes(StandardCharsets.UTF_8));
        return producerRecord;
    }

    private String getTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalDateTime.now().format(dateTimeFormatter);
    }
}
