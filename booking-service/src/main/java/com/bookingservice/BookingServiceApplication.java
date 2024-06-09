package com.bookingservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookingServiceApplication {
	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure()
//				.directory("./booking-service")
				.directory("./")
				.filename(".env")
				.load();
		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});

		SpringApplication.run(BookingServiceApplication.class, args);
	}

}
