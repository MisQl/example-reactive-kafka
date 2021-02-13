package com.example.demo.kafka;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.example.demo.kafka.KafkaConfig.SPRING_GROUP_ID;
import static com.example.demo.kafka.KafkaConfig.SPRING_TOPIC;

@Component
@KafkaListener(groupId = SPRING_GROUP_ID, topics = SPRING_TOPIC)
public class KafkaConsumer {

    @KafkaHandler
    public void handleKafkaMessage1(KafkaMessage1 message) {
        System.out.println("---> " + message);
    }

    @KafkaHandler
    public void handleKafkaMessage2(KafkaMessage2 message) {
        System.out.println("---> " + message);
    }
}
