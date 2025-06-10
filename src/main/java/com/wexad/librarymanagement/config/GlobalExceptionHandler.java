package com.wexad.librarymanagement.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex, Model model) {
        logger.error("An unexpected error occurred: {}", ex.getMessage(), ex);
//        model.addAttribute("errorMessage", "An unexpected error occurred.");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/general-error";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(NoHandlerFoundException ex, Model model) {
        logger.warn("Page not found: {}", ex.getRequestURL());
        model.addAttribute("errorMessage", "Page not found.");
        return "error/404";
    }
}



