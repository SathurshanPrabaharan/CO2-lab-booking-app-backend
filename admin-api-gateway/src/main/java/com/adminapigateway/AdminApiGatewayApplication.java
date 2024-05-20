package com.adminapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AdminApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApiGatewayApplication.class, args);
    }
}
