package com.example.mailservice.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private JavaMailSender mailSender;


    public void sendWelcomeMail(String to, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Hoşgeldiniz!");
        message.setText("Merhaba " + name + ",\n\nSisteme hoşgeldiniz!\n\nBaşarılar dileriz.");
        mailSender.send(message);
    }



}
