package com.notificationservice.kafka;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notificationservice.Models.NotificationMessage;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * to convert NotificationMessage objects into a byte array (byte[]) format suitable
 * for transmission over Kafka topics.
 */
public class NotificationMessageSerializer implements Serializer<NotificationMessage>{
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     *  used for any configuration required by the serializer.
     *  In this case, no specific configuration is needed,
     *  so it's empty
     */
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No configuration needed
    }

    /**
     *used to convert Java objects to byte arrays directly
     */
    @Override
    public byte[] serialize(String topic, NotificationMessage data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing NotificationMessage", e);
        }
    }

    /**
     *  used to release any resources held by the serializer. In this case,
     *  since there are no resources to release, it remains empty.
     */
    @Override
    public void close() {
        // No resources to close
    }
}

