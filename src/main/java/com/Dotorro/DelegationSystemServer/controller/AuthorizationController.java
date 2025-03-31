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
@RequestMapping("/authorization")
@CrossOrigin(origins = "*")
public class AuthorizationController {

    private final AuthorizationService authorizationService;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthorizationController(AuthorizationService authorizationService,
                                   AuthenticationService authenticationService,
                                   UserService userService) {
        this.authorizationService = authorizationService;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody String email, @RequestBody String password) {
        User user = userService.getUserByEmail(email);

        if (!authenticationService.matchPassword(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = authorizationService.generateToken(userService.convertToDTO(user));
        ResponseCookie cookie = ResponseCookie.from("jwt", token).httpOnly(true).path("/")
                .maxAge(3600).build();

        return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).body("Login successful");
    }
}

