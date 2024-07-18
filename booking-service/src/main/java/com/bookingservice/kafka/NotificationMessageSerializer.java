package com.bookingservice.kafka;
import com.bookingservice.Models.NotificationMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class NotificationMessageSerializer implements Serializer<NotificationMessage>{
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No configuration needed
        //default
    }

    @Override
    public byte[] serialize(String topic, NotificationMessage data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing NotificationMessage", e);
        }
    }

    @Override
    public void close() {
        // No resources to close
    }
}

