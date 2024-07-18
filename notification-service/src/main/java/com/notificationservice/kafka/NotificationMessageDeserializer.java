package com.notificationservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notificationservice.Models.NotificationMessage;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

/**
 * to convert byte arrays received from Kafka back into objects.
 */
public class NotificationMessageDeserializer implements Deserializer<NotificationMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No configuration needed
    }

    /**
     * takes a byte array (data) received from Kafka and converts
     * it back into a  object using Jackson's

     */
    @Override
    public NotificationMessage deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, NotificationMessage.class);
        } catch (IOException e) {
            throw new RuntimeException("Error deserializing NotificationMessage", e);
        }
    }

    @Override
    public void close() {
        // No resources to close
    }
}
