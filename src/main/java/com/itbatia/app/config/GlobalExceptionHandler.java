package com.itbatia.app.config;

import com.itbatia.app.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice()
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleException(UserNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleException(UsernameNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrganizationNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleException(OrganizationNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleException(ItemNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity<ErrorResponse> handleException(AuthenticationException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<ErrorResponse> handleException(BadCredentialsException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleException(OrderNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenChangeException.class)
    private ResponseEntity<ErrorResponse> handleException(ForbiddenChangeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleException(NotificationNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
