package com.javariga6.yugioh.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleApiException(
            ResourceNotFoundException ex) {
        ErrorResponse response =
                new ErrorResponse("error-0001",
                        "Resource not found!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
