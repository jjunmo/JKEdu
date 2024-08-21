package com.example.jkedudemo.module.handler;

import lombok.Getter;

@Getter
public class MyInternalServerException extends RuntimeException {

    public MyInternalServerException(String message) {
        super(message);
    }
}