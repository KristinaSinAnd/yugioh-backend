package com.javariga6.yugioh.exceptions;

public class BadDataInRequestException extends RuntimeException {
    public BadDataInRequestException() {
    }

    public BadDataInRequestException(String message) {
        super(message);
    }

    public BadDataInRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadDataInRequestException(Throwable cause) {
        super(cause);
    }

    public BadDataInRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
