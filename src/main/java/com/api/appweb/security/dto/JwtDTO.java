package com.api.appweb.security.dto;

public class JwtDTO {
    private String token;

    public JwtDTO() {
    }

    public JwtDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
