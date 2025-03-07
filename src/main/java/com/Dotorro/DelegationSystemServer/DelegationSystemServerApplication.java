package com.Dotorro.DelegationSystemServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class DelegationSystemServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DelegationSystemServerApplication.class, args);
	}

	@GetMapping("/")
	public String hello() {
		return "Hello, Spring Boot!";
	}
}
