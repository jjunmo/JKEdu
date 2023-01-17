package com.example.jkedudemo.module.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class MyControllerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(MyUnAuthorizedException.class)
    public ResponseEntity<MyErrorBody> handleUnAuthorizedException(MyUnAuthorizedException e) {
        return new ResponseEntity<>(
                new MyErrorBody("access_expired","401",e.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }


    @ExceptionHandler(MyForbiddenException.class)
    public ResponseEntity<MyErrorBody> handleForbiddenException(MyForbiddenException e) {
        return new ResponseEntity<>(
                new MyErrorBody("forbidden","403",e.getMessage()),
                HttpStatus.FORBIDDEN
        );
    }


    @ExceptionHandler(MyNotFoundException.class)
    public ResponseEntity<MyErrorBody> handleNotFoundException(MyNotFoundException e) {
        return new ResponseEntity<>(
                new MyErrorBody("not_found","404",e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }


    @ExceptionHandler(MyInternalServerException.class)
    public ResponseEntity<MyErrorBody> handleInternalServerException(MyInternalServerException e) {
        return new ResponseEntity<>(
                new MyErrorBody("server_error","500",e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


    @ExceptionHandler(MyBadRequestException.class)
    public final ResponseEntity<MyErrorBody> handleBadRequestException(Exception e) {
        return new ResponseEntity<>(
                new MyErrorBody("bad_request","400",e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

}