package ru.sumarokov.premium_calculation.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.sumarokov.premium_calculation.exception.EntityNotFoundException;

@ControllerAdvice
public class ExceptionsController {

    @ExceptionHandler({AccessDeniedException.class})
    public String handleAccessDeniedException(AccessDeniedException accessDeniedException, Model model) {
        model.addAttribute("message", accessDeniedException.getMessage());
        return "exception/access_denied";
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public String entityNotFoundException(EntityNotFoundException entityNotFoundException, Model model) {
        model.addAttribute("message", entityNotFoundException.getMessage());
        return "exception/not_found";
    }
}
