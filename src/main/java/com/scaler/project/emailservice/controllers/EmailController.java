package com.scaler.project.emailservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.project.emailservice.dtos.EmailDto;
import com.scaler.project.emailservice.services.EmailService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {


    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper;
    public EmailController(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate){
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/send")
    public EmailDto sendEmail(@RequestBody EmailDto emailDto) {
        try {
            kafkaTemplate.send("email", objectMapper.writeValueAsString(emailDto));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return emailDto;
    }
}
