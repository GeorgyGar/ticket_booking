package ru.stmLabs.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "Сообщение об ошибке")
public record ErrorResponse(
        @Schema(description = "Текст ошибки", example = "Ошибка валидации")
        String message,

        @Schema(description = "Детали ошибки", example = "{\"field\": \"Описание ошибки\"}")
        Map<String, String> details
) {}