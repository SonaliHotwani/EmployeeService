package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.exception.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@ControllerAdvice
public class EmployeeResponseEntityExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handle(MethodArgumentNotValidException ex, WebRequest request) {
        final String message = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage()).collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(
                        "{" +
                        "  \"message\": \"" + message + "\"" +
                        "}");
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    protected ResponseEntity<Object> handle(HttpMessageNotReadableException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        "{" +
                        "  \"message\": \"" + ex.getCause().getMessage() + "\"" +
                        "}");
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handle(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        "{" +
                        "  \"message\": \"Internal Server Error\"" +
                        "}");
    }

    @ExceptionHandler(value = {EmployeeNotFoundException.class})
    protected ResponseEntity<Object> handle(EmployeeNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        "{" +
                        "  \"message\": \"" + ex.getMessage() + "\"" +
                        "}");
    }

    @ExceptionHandler(value = {NumberFormatException.class})
    protected ResponseEntity<Object> handle(NumberFormatException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        "{" +
                        "  \"message\": \"Employee Id must be a numeric value\"" +
                        "}");
    }
}
