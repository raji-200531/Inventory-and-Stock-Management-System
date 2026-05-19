package com.inventory.stockmanagement.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ResourceNotFoundException.class, DuplicateResourceException.class, InvalidOperationException.class})
    public String handleKnown(RuntimeException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleUnknown(Exception ex, Model model) {
        model.addAttribute("errorMessage", "Unexpected error: " + ex.getMessage());
        return "error";
    }
}
