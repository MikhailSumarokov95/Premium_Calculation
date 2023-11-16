package ru.sumarokov.premium_calculation.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.access.AccessDeniedException;
import ru.sumarokov.premium_calculation.exception.EntityNotFoundException;

@ControllerAdvice
public class ExceptionsController {

    @ExceptionHandler({AccessDeniedException.class})
    public String handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        return "exception/accessDenied";
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public String entityNotFoundException(AccessDeniedException accessDeniedException) {
        return "exception/notFound";
    }
}
