package com.scaler.project.emailservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.project.emailservice.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Service
public class EmailService {
    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper;

    @Value("${email.address}")
    private String fromEmail;

    @Value("${email.password}")
    private String password;

    public void sendMailSSL(String to, String subject, String body) {
        System.out.println("to: " + to);
        System.out.println("subject: " + subject);
        System.out.println("body: " + body);

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "587"); //SMTP Port
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.starttls.required", "true");



        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getInstance(props, auth);
        EmailUtil.sendEmail(session, to, subject, body);
    }
}
