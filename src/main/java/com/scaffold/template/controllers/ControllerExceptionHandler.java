package com.scaffold.template.controllers;

import com.scaffold.template.dtos.common.ErrorApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorApi> handleException(Exception exception) {
        ErrorApi error = buildError(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorApi> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ErrorApi error = buildError(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    private ErrorApi buildError(String message, HttpStatus status){
        return ErrorApi.builder()
                .timestamp(String.valueOf(Timestamp.from(ZonedDateTime.now().toInstant())))
                .error(status.getReasonPhrase())
                .status(status.value())
                .message(message)
                .build();
    }
}