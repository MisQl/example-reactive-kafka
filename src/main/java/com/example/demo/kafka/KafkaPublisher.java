package com.example.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;

import static com.example.demo.kafka.KafkaConfig.SPRING_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaPublisher implements CommandLineRunner {

    private final ReactiveKafkaProducerTemplate<String, Object> producerTemplate;

    @Override
    public void run(String... args) {
        Flux.interval(Duration.ofSeconds(3))
                .flatMap(i -> producerTemplate.send(SPRING_TOPIC, new KafkaMessage1("message-" + i)))
                .subscribe();

        Flux.interval(Duration.ofSeconds(5))
                .flatMap(i -> producerTemplate.send(SPRING_TOPIC, new KafkaMessage2("message-" + i)))
                .subscribe();
    }
}
