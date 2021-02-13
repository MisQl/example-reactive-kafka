package com.example.demo.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;

import static com.example.demo.kafka.KafkaConfig.SPRING_TOPIC;

@Component
@RequiredArgsConstructor
public class KafkaPublisher implements CommandLineRunner {

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @Override
    public void run(String... args) {
        Flux.interval(Duration.ofSeconds(5)).subscribe(i -> kafkaTemplate.send(SPRING_TOPIC, new KafkaMessage1("message-" + i)));
        Flux.interval(Duration.ofSeconds(7)).subscribe(i -> kafkaTemplate.send(SPRING_TOPIC, new KafkaMessage2("message-" + i)));
    }
}
