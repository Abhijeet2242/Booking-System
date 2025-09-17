package com.multigenesys.booking_system.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidations(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistException(UserAlreadyExistException ex){
        log.warn("User already Exist {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message" , "User already exists");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex){
        log.warn("Resource not found {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message" , "resource not found");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handleResourceAlreadyExist(ResourceAlreadyExistException ex){
        log.warn("Resource already exist {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message" , "resource already exist");
        return ResponseEntity.badRequest().body(errors);
    }
}
