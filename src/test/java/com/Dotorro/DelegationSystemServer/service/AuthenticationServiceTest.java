package com.Dotorro.DelegationSystemServer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private AuthenticationService authenticationService;

    @BeforeEach
    void setup() {
        authenticationService = new AuthenticationService();
    }

    @Test
    void shouldThrowExceptionWhenInvalidEmail() {
        String invalidEmail = "not-an-email";

        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.validateEmail(invalidEmail);
        });
    }

    @Test
    void shouldPassWhenValidEmail() {
        String validEmail = "john@doe.com";

        assertDoesNotThrow(() ->  authenticationService.validateEmail(validEmail));
    }

    @Test
    void shouldThrowExceptionWhenInvalidPassword() {
        String invalidPassword = "asd";

        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.validatePassword(invalidPassword);
        });
    }

    @Test
    void shouldPassWhenValidPassword() {
        String validPassword = "a1A!a1A!";

        assertDoesNotThrow(() ->  authenticationService.validatePassword(validPassword));
    }
}