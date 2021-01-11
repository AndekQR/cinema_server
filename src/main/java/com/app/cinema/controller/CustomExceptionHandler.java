package com.app.cinema.controller;

import com.app.cinema.helper.ChairReservedException;
import com.app.cinema.model.ErrorResponse;
import com.app.cinema.helper.NotFoundInDB;
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
    public ResponseEntity<ErrorResponse> handleException(BadCredentialsException exception, WebRequest request){
        return asResponseEntity(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleException(NullPointerException exception, WebRequest request) {
        return asResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleException(InvalidJwtAuthenticationException exception, WebRequest request) {
        return asResponseEntity(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException exception, WebRequest request) {
        return asResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UsernameNotFoundException exception, WebRequest request) {
        return asResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyInDatabaseException.class)
    public ResponseEntity<ErrorResponse> handleException(UserAlreadyInDatabaseException exception, WebRequest request) {
        return asResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundInDB.class)
    public ResponseEntity<ErrorResponse> handleException(NotFoundInDB exception, WebRequest request) {
        return asResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ChairReservedException.class)
    public ResponseEntity<ErrorResponse> handleException(ChairReservedException exception, WebRequest request) {
        return asResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


    private ResponseEntity<ErrorResponse> asResponseEntity( String errorMessage, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResponse, status);
    }
}
