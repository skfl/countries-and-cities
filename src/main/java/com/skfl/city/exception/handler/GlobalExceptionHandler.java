package com.skfl.city.exception.handler;

import com.skfl.city.dto.response.ApplicationErrorResponse;
import com.skfl.city.dto.response.ApplicationValidationErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApplicationErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
        var message = "Entity Not Found Exception";
        log.warn(message, e);
        return ApplicationErrorResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(message)
                .errorMessage(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationValidationErrorResponse handleValidationExceptions(MethodArgumentNotValidException e) {
        var message = "MethodArgumentNotValid Exception";
        log.warn(message, e);
        Map<String, String> violations = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            violations.put(fieldName, errorMessage);
        });
        return ApplicationValidationErrorResponse.builder()
                .message(message)
                .errorMessage(e.getMessage())
                .violations(violations)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApplicationErrorResponse handleRuntimeException(Exception e) {
        var message = "Unhandled exception";
        log.warn(message, e);
        return ApplicationErrorResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(message)
                .errorMessage(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
