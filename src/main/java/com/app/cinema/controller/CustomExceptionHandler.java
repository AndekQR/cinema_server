package com.app.cinema.controller;

import com.app.cinema.helper.ErrorResponse;
import com.app.cinema.helper.UserAlreadyInDatabaseException;
import com.app.cinema.security.jwt.InvalidJwtAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
    public ResponseEntity<ErrorResponse> handleCredentialException(BadCredentialsException exception, WebRequest request){
        return asResponseEntity(exception.getMessage(), "Invalid username/password supplied", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException exception, WebRequest request) {
        return asResponseEntity(exception.getMessage(), "Internal error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJwtAuthentication(InvalidJwtAuthenticationException exception, WebRequest request) {
        return asResponseEntity(exception.getMessage(), "Expired or invalid JWT token", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest request) {
        return asResponseEntity(exception.getMessage(), "Invalid argument", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJwtAuthentication(UsernameNotFoundException exception, WebRequest request) {
        return asResponseEntity(exception.getMessage(), "This user is not registered", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyInDatabaseException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJwtAuthentication(UserAlreadyInDatabaseException exception, WebRequest request) {
        return asResponseEntity(exception.getMessage(), "try login", HttpStatus.CONFLICT);
    }


    private ResponseEntity<ErrorResponse> asResponseEntity(String exMessage, String errorMessage, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(errorMessage, exMessage);
        return new ResponseEntity<>(errorResponse, status);
    }
}
