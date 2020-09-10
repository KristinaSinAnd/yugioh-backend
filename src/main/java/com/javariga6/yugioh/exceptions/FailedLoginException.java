package com.javariga6.yugioh.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;

public class FailedLoginException extends HttpClientErrorException {
    public FailedLoginException(HttpStatus statusCode) {
        super(statusCode);
    }

    public FailedLoginException(HttpStatus statusCode, String statusText) {
        super(statusCode, statusText);
    }

    public FailedLoginException(HttpStatus statusCode, String statusText, byte[] body, Charset responseCharset) {
        super(statusCode, statusText, body, responseCharset);
    }

    public FailedLoginException(HttpStatus statusCode, String statusText, HttpHeaders headers, byte[] body, Charset responseCharset) {
        super(statusCode, statusText, headers, body, responseCharset);
    }

    public FailedLoginException(String message, HttpStatus statusCode, String statusText, HttpHeaders headers, byte[] body, Charset responseCharset) {
        super(message, statusCode, statusText, headers, body, responseCharset);
    }
}
