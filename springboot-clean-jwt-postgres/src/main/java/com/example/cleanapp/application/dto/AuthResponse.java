package com.example.cleanapp.application.dto;

public class AuthResponse {
    private String token;
    private String username;
    private String email;

    public AuthResponse() {}

    public AuthResponse(String token, String username, String email) {
        this.token = token;
        this.username = username;
        this.email = email;
    }

    public String getToken() { return token; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }

    public void setToken(String token) { this.token = token; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
}