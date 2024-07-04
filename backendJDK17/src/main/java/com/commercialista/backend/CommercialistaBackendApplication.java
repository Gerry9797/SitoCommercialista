package com.commercialista.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CommercialistaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommercialistaBackendApplication.class, args);
	}

}
