package com.notificationservice.Service;
import com.notificationservice.Models.NotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JavaMailSender mailSender;

    @KafkaListener(topics = "booking-notifications", groupId = "notification-group")
    public void receiveMessage(String messageJson) {
        try {
            NotificationMessage message = objectMapper.readValue(messageJson, NotificationMessage.class);
            logger.info("Received message from Kafka: {}", message);
            sendEmail(message);
        } catch (Exception e) {
            logger.error("Error processing Kafka message: {}", e.getMessage(), e);
        }
    }

    public void sendEmail(NotificationMessage message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(message.getTo());
            mailMessage.setSubject(message.getSubject());
            mailMessage.setText(message.getBody());

            logger.info("Sending email to: {}", message.getTo());
            mailSender.send(mailMessage);
            logger.info("Email sent to: {}", message.getTo());
        } catch (Exception e) {
            logger.error("Error sending email",e.getMessage(), e);
        }
    }
}
