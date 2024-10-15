package com.saandeepkotte.CatalogManagement.advice;

import com.saandeepkotte.CatalogManagement.exceptions.InvalidCredentialsException;
import com.saandeepkotte.CatalogManagement.exceptions.InvalidIdException;
import com.saandeepkotte.CatalogManagement.exceptions.InvalidSearchException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    Map<String, String> errorMap = new HashMap<>();

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArguments(MethodArgumentNotValidException e) {
        e.getBindingResult().getFieldErrors().stream()
                .forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidIdException.class)
    public Map<String, String> handleInvalidId(InvalidIdException e) {
        errorMap.put("error", e.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidSearchException.class)
    public Map<String, String> handleInvalidSearch(InvalidSearchException e) {
        errorMap.put("error", e.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public Map<String, String> handleUserNotFound(UsernameNotFoundException e) {
        errorMap.put("error", e.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidCredentialsException.class)
    public Map<String, String> handleInvalidCredentials(InvalidCredentialsException e) {
        errorMap.put("error", e.getMessage());
        return errorMap;
    }
}
