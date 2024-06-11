package com.configurationservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConfigurationServiceApplication {
	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure()
                .directory("./configuration-service")
//				.directory("./")
				.filename(".env")
				.load();

		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});

		SpringApplication.run(ConfigurationServiceApplication.class, args);
	}

}
