package com.project.mandate_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MandateServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MandateServiceApplication.class, args);
	}

}
