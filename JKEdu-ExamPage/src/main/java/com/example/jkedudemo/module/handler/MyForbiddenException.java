package com.example.jkedudemo.module.handler;

import lombok.Getter;

@Getter
public class MyForbiddenException extends RuntimeException {

    public MyForbiddenException(String message) {
        super(message);
    }
}
