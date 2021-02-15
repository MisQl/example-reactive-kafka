package com.example.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.time.Duration;

import static com.example.demo.kafka.KafkaConfig.SPRING_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaPublisher implements CommandLineRunner {

    private final KafkaSender<String, String> sender;

    @Override
    @SneakyThrows
    public void run(String... args) {
        var flux = Flux.interval(Duration.ofSeconds(3))
                .map(i -> SenderRecord.create(new ProducerRecord<>(SPRING_TOPIC, "Key_" + i, "Message_" + i), i));

        sender.send(flux).subscribe();
    }
}
