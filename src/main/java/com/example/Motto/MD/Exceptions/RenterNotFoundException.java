package com.example.Motto.MD.Exceptions;

public class RenterNotFoundException extends RuntimeException {
    public RenterNotFoundException(String message) {
        super(message);
    }

    public RenterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
