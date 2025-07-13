package com.workfolder.work.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workfolder.work.event.UserRegisteredEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserRegisteredEvent(String email, String name) {
        try {
            UserRegisteredEvent event = new UserRegisteredEvent(email, name);
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("user-registered", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
