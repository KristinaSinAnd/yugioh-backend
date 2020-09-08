package com.javariga6.yugioh.exceptions;

import org.hibernate.TransientPropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

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

    @ExceptionHandler(value = {ReferencedResourceNotFoundException.class, IdInUseException.class, BadDataInRequestException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleApiException(
            RuntimeException ex) {
        ErrorResponse response =
                new ErrorResponse("error-0002",
                        "Bad Request!");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EmailInUseException.class)
    public ResponseEntity<ErrorResponse> handleApiException(
            DataConflictException ex) {
        ErrorResponse response =
                new ErrorResponse("error-0003",
                        "Data conflict!");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

}
