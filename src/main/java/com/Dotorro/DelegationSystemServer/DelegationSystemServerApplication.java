package com.Dotorro.DelegationSystemServer;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class DelegationSystemServerApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(DelegationSystemServerApplication.class, args);
	}

	@GetMapping("/")
	public String hello() {
		return "Hello, Spring Boot!";
	}
}
