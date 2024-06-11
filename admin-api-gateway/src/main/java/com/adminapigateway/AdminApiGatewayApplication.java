package com.adminapigateway;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdminApiGatewayApplication {
    public static void main(String[] args) {
//        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        Dotenv dotenv = Dotenv.configure()
                .directory("./admin-api-gateway")
//                .directory("./")
                .filename(".env")
                .load();

        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });

        SpringApplication.run(AdminApiGatewayApplication.class, args);
    }


}
