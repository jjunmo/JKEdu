package com.example.jkedudemo.module.handler;

import lombok.Getter;

@Getter
public class MyUnAuthorizedException extends RuntimeException{

    public MyUnAuthorizedException(String message) {
        super(message);
    }
}
