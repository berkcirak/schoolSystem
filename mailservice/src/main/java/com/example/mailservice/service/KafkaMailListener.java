package com.example.mailservice.service;

import com.example.mailservice.event.UserRegisteredEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMailListener {

    private final MailService mailService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaMailListener(MailService mailService) {
        this.mailService = mailService;
    }

    @KafkaListener(topics = "user-registered", groupId = "mail-group")
    public void listen(String message) {
        try {
            UserRegisteredEvent event = objectMapper.readValue(message, UserRegisteredEvent.class);
            mailService.sendWelcomeMail(event.getEmail(), event.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
