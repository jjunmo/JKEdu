package com.example.jkedudemo.module.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class MyControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MyUnAuthorizedException.class)
    public ResponseEntity<MyErrorBody> handleForbiddenException(MyUnAuthorizedException e) {
        return new ResponseEntity<>(
                new MyErrorBody(e.getMessage(),"401"),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ResponseBody
    @ExceptionHandler(MyForbiddenException.class)
    public ResponseEntity<MyErrorBody> handleForbiddenException(MyForbiddenException e) {
        return new ResponseEntity<>(
                new MyErrorBody(e.getMessage(),"403"),
                HttpStatus.FORBIDDEN
        );
    }

    @Deprecated
    @ResponseBody
    @ExceptionHandler(MyNotFoundException.class)
    public ResponseEntity<MyErrorBody> handleNotFoundException(MyNotFoundException e) {
        return new ResponseEntity<>(
                new MyErrorBody(e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ResponseBody
    @ExceptionHandler(MyInternalServerException.class)
    public ResponseEntity<MyErrorBody> handleInternalServerException(MyInternalServerException e) {
        return new ResponseEntity<>(
                new MyErrorBody(e.getMessage(),"400"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}