package com.notificationservice.Models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NotificationMessageTest {

    @Test
    public void testAllArgsConstructor() {
        NotificationMessage notificationMessage = new NotificationMessage("test@example.com", "Test Subject", "Test Body");
        assertEquals("test@example.com", notificationMessage.getTo());
        assertEquals("Test Subject", notificationMessage.getSubject());
        assertEquals("Test Body", notificationMessage.getBody());
    }

    @Test
    public void testNoArgsConstructor() {
        NotificationMessage notificationMessage = new NotificationMessage();
        assertNull(notificationMessage.getTo());
        assertNull(notificationMessage.getSubject());
        assertNull(notificationMessage.getBody());

        notificationMessage.setTo("test@example.com");
        notificationMessage.setSubject("Test Subject");
        notificationMessage.setBody("Test Body");

        assertEquals("test@example.com", notificationMessage.getTo());
        assertEquals("Test Subject", notificationMessage.getSubject());
        assertEquals("Test Body", notificationMessage.getBody());
    }

    @Test
    public void testSettersAndGetters() {
        NotificationMessage notificationMessage = new NotificationMessage();
        notificationMessage.setTo("test@example.com");
        notificationMessage.setSubject("Test Subject");
        notificationMessage.setBody("Test Body");

        assertEquals("test@example.com", notificationMessage.getTo());
        assertEquals("Test Subject", notificationMessage.getSubject());
        assertEquals("Test Body", notificationMessage.getBody());
    }

    @Test
    public void testToString() {
        NotificationMessage notificationMessage = new NotificationMessage("test@example.com", "Test Subject", "Test Body");
        String expected = "NotificationMessage{to='test@example.com', subject='Test Subject', body='Test Body'}";
        assertEquals(expected, notificationMessage.toString());
    }
}
