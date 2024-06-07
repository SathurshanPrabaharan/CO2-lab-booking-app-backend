package com.bookingservice.Configs;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DotenvConfig {
    @PostConstruct
    public void init() {
        Dotenv dotenv = Dotenv.configure()
                .directory("./booking-service")
                .filename(".env")
                .load();
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
    }
}