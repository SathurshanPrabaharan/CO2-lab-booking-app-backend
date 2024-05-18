package com.configurationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConfigurationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigurationServiceApplication.class, args);
	}

}
