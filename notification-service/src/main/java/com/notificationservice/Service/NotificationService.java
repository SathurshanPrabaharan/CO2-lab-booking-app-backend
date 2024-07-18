package com.notificationservice.Service;
import com.notificationservice.Models.NotificationMessage;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(message.getTo());
            helper.setSubject(message.getSubject());
            helper.setText(message.getBody(), true); // Set the boolean flag to true to send HTML content
            logger.info("Sending email to: {}", message.getTo());
            mailSender.send(mimeMessage);
            logger.info("Email sent to: {}", message.getTo());
        } catch (Exception e) {
            logger.error("Error sending email",e.getMessage(), e);
        }
    }
}
