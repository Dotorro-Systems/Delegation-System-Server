package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.repository.UserRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationService {

    private final UserService userService;

    public AuthenticationService(UserService userService) {
        this.userService = userService;
    }

    private boolean EmailValidation(String email)
    {
        Pattern compiledPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = compiledPattern.matcher(email);

        return matcher.matches();
    }

    private boolean PasswordValidation(String password)
    {
        Pattern compiledPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[\\W_])[^ \\p{So}]{8,20}$");
        Matcher matcher = compiledPattern.matcher(password);

        return matcher.matches();
    }
}
