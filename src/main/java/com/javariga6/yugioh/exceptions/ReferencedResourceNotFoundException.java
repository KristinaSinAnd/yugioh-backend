package com.javariga6.yugioh.exceptions;

public class ReferencedResourceNotFoundException extends RuntimeException{
    public ReferencedResourceNotFoundException() {
    }

    public ReferencedResourceNotFoundException(String message) {
        super(message);
    }

    public ReferencedResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReferencedResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public ReferencedResourceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
