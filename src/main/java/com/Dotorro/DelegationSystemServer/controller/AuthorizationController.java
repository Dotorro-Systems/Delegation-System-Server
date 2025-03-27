package com.Dotorro.DelegationSystemServer.controller;

import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.service.AuthenticationService;
import com.Dotorro.DelegationSystemServer.service.AuthorizationService;
import com.Dotorro.DelegationSystemServer.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authorizations")
@CrossOrigin(origins = "*") // Allow frontend requests
public class AuthorizationController {

    private final AuthorizationService authorizationService;
    private final AuthenticationService authenticationService;


    public AuthorizationController(AuthorizationService authorizationService,
                                   AuthenticationService authenticationService) {
        this.authorizationService = authorizationService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO, String password) {

        if (!authenticationService.matchPassword(userDTO.getPassword(), password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = authorizationService.generateToken(userDTO);
        ResponseCookie cookie = ResponseCookie.from("jwt", token).httpOnly(true).path("/")
                .maxAge(3600).build();

        return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body("Login successful");
    }
}

