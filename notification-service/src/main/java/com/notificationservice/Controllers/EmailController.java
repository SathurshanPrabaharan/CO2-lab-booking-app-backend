package com.notificationservice.Controllers;

import com.notificationservice.Services.Impls.EmailService;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/notifications")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String sendEmail() {
        try {
            emailService.sendEmail();
            return "Email sent successfully!";
        } catch (IOException e) {
            return "Error sending email: " + e.getMessage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static class EmailRequest {
        private String to;
        private String subject;
        private String bodyContent;

        // Getters and Setters

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getBodyContent() {
            return bodyContent;
        }

        public void setBodyContent(String bodyContent) {
            this.bodyContent = bodyContent;
        }
    }
}
