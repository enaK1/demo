package com.example.demo.dto;

public class JwtAuthenticationResponse {
    private String token;
    private String tokenType = "Bearer";
    private UserSummary user;

    public JwtAuthenticationResponse(String token, UserSummary user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public UserSummary getUser() {
        return user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setUser(UserSummary user) {
        this.user = user;
    }
}
