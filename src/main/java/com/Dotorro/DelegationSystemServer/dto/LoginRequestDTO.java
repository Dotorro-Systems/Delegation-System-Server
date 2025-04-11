package com.Dotorro.DelegationSystemServer.dto;

public class LoginRequestDTO {
    private String email;
    private String password;
    private Boolean staySignedIn;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStaySignedIn() {
        return staySignedIn;
    }

    public void setStaySignedIn(Boolean staySignedIn) {
        this.staySignedIn = staySignedIn;
    }
}
