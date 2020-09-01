package com.javariga6.yugioh.exceptions;

public class IdInUseException extends RuntimeException{
    public IdInUseException() {
    }

    public IdInUseException(String message) {
        super(message);
    }

    public IdInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdInUseException(Throwable cause) {
        super(cause);
    }

    public IdInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
