package com.Dotorro.DelegationSystemServer;

import com.Dotorro.DelegationSystemServer.dto.LoginRequestDTO;
import com.Dotorro.DelegationSystemServer.exceptions.ApiException;
import com.Dotorro.DelegationSystemServer.service.JWTService;
import com.Dotorro.DelegationSystemServer.service.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class DelegationSystemServerApplication {

	@Autowired
	JWTService jwtService;

	public static void main(String[] args) {
		try {
			Dotenv dotenv = Dotenv.load();
			dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		}
		catch (Exception e)
		{

		}

		SpringApplication.run(DelegationSystemServerApplication.class, args);
	}

	@GetMapping("/")
	public String hello() {
		return "Hello, Spring Boot!";
	}

	@GetMapping(value = "/authenticated")
	public boolean isAuthenticated(HttpServletRequest request) throws ApiException {
		String token = null;
		try {
			token = jwtService.getTokenFromRequest(request);
		}
		catch (Exception e) {
			return false;
		}

		return jwtService.validateToken(token);
	}
}
