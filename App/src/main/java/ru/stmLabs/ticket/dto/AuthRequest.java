package ru.stmLabs.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на аутентификацию")
public class AuthRequest {
    @Schema(description = "Логин", example = "user123")
    private String login;

    @Schema(description = "Пароль", example = "password123")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
