package com.example.demo.kafka;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.example.demo.kafka.KafkaConfig.SPRING_GROUP_ID;
import static com.example.demo.kafka.KafkaConfig.SPRING_TOPIC;

@Component
@KafkaListener(topics = SPRING_TOPIC, groupId = SPRING_GROUP_ID)
public class KafkaConsumer {

    @KafkaHandler
    public void consume(@Payload KafkaMessage1 kafkaMessage) {
        System.out.println("---> receive: " + kafkaMessage);
    }

    @KafkaHandler
    public void consume(@Payload KafkaMessage2 kafkaMessage, @Header("token") String token) {
        System.out.println("---> receive: " + kafkaMessage + " token: " + token);
    }
}
