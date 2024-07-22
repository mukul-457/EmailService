package com.scaler.project.emailservice.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.project.emailservice.dtos.EmailDto;
import com.scaler.project.emailservice.services.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SendEmailEvenListner {

    private ObjectMapper objectMapper;
    private EmailService emailService;

    public SendEmailEvenListner(EmailService emailService, ObjectMapper objectMapper) {
        this.emailService = emailService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "email", groupId = "group_id")
    public  void consume(String message) {
        try {
            System.out.println("Consumed message: ");
            EmailDto emailDto = objectMapper.readValue(message, EmailDto.class);
            emailService.sendMailSSL(emailDto.getTo(), emailDto.getSubject(), emailDto.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
