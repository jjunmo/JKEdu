package com.example.jkedudemo.module.handler;

import lombok.Getter;

@Getter
public class MyUnAuthorizedException extends RuntimeException{

    private final String status="401";
    public MyUnAuthorizedException(String message) {
        super(message);
    }
}
