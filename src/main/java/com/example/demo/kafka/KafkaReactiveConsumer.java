package com.example.demo.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;

// @Component
@RequiredArgsConstructor
public class KafkaReactiveConsumer implements CommandLineRunner {

    private final KafkaReceiver<String, Object> kafkaReceiver;

    @Override
    public void run(String... args) {
        kafkaReceiver.receive()
                .doOnNext(r -> r.receiverOffset().acknowledge())
                .map(ReceiverRecord::value)
                .subscribe(m -> System.out.println("---> receive: " + m));
    }
}
