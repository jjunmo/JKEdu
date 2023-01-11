package com.example.jkedudemo.module.handler;

import lombok.Getter;

@Getter
public class MyBadRequestException extends RuntimeException{
    public MyBadRequestException(String message) {
        super(message);
    }
}
