package com.msvc_news.msvc_news.controllers;

import com.msvc_news.msvc_news.models.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDate;
import java.util.Collections;

import static com.msvc_news.msvc_news.utils.ErrorCatalog.*;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    // Ocurre cuando la validación falla en los parámetros de un método del controlador
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ErrorResponse handleValidationException(HandlerMethodValidationException ex) {
        log.error("Error: ", ex);
        return ErrorResponse.builder()
                .code(INVALID_PARAMETER.getCode())
                .message(INVALID_PARAMETER.getMessage())
                .details(Collections.singletonList(ex.getMessage()))
                .timestamp(LocalDate.now().toString())
                .build();
    }

    // Ocurre cuando la validación falla en los parámetros de un método del controlador
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        log.error("Error: ", ex);
        return ErrorResponse.builder()
                .code(INVALID_PARAMETER.getCode())
                .message(INVALID_PARAMETER.getMessage())
                .details(ex.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList())
                .timestamp(LocalDate.now().toString())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception ex) {
        log.error("Error: ", ex);
        return ErrorResponse.builder()
                .code(INTERNAL_SERVER_ERROR.getCode())
                .message(INTERNAL_SERVER_ERROR.getMessage())
                .details(Collections.singletonList(ex.getMessage()))
                .timestamp(LocalDate.now().toString())
                .build();
    }

}
