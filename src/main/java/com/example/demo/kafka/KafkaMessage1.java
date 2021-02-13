package com.example.demo.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessage1 {

    private String id = UUID.randomUUID().toString();
    private String message;

    public KafkaMessage1(String message) {
        this.message = "kafka-message-1-" + message;
    }
}
