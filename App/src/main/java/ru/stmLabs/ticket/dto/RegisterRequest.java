package ru.stmLabs.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema (description = "Запрос на регистрацию")
public class RegisterRequest {
    @Schema (description = "Полное имя")
    private String fullName;

    @Schema (description = "Логин")
    private String userLogin;

    @Size(min = 6, message = "Password must be at least 6 characters")
    @Schema (description = "Пароль")
    private String password;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
