package com.Dotorro.DelegationSystemServer.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthenticationService {

    public AuthenticationService() { }

    public boolean validateEmail(String email)
    {
        Pattern compiledPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = compiledPattern.matcher(email);

        boolean result = matcher.matches();

        if (!result)
            throw new IllegalArgumentException("Email is not valid");

        return true;
    }

    public boolean validatePassword(String password)
    {
        Pattern compiledPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[\\W_])[^ \\p{So}]{8,20}$");
        Matcher matcher = compiledPattern.matcher(password);

        boolean result = matcher.matches();

        if (!result)
            throw new IllegalArgumentException("Password doesn't meet requirements");

        return true;
    }

    public String hashPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public boolean matchPassword(String password, String hashedPassword)
    {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, hashedPassword);
    }
}
