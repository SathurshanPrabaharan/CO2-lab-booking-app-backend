package com.bookingservice.Models;
/**
 * it used to message that will be send to Kafka
 * and eventually consumed by the notification-service
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationMessage {
    private String to;
    private String subject;
    private String body;

    //provide a json format
    @Override
    public String toString() {
        return "NotificationMessage{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
