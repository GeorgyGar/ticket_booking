package ru.stmLabs.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с JWT токенами")
public class AuthResponse {
    @Schema(description = "Access токен")
    private String accessToken;


    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
