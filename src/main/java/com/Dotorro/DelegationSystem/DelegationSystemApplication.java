package com.Dotorro.DelegationSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class DelegationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DelegationSystemApplication.class, args);
	}

	@GetMapping("/")
	public String hello() {
		return "Hello, Spring Boot!";
	}
}
