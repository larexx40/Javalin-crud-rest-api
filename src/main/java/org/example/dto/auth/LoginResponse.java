package org.example.dto.auth;

import org.example.entity.User;

public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private User user;

    public LoginResponse(String accessToken, String refreshToken, User user) {
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;

    }

    // Getters and Setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}