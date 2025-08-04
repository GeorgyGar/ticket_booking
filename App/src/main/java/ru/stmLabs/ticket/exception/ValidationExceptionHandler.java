package ru.stmLabs.ticket.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.stmLabs.ticket.dto.ErrorResponse;

import java.util.*;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                field -> errors.put(field.getField(), field.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(new ErrorResponse("Ошибка валидации", errors));
    }
}
