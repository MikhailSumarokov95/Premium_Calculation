package ru.sumarokov.premium_calculation.exception;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {}

    public AccessDeniedException(String message) {
        super(message);
    }
}
