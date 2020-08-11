package com.javariga6.yugioh.model;

public class AuthenticationResult {

    private String token;

    public AuthenticationResult(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
