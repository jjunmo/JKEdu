package com.example.jkedudemo.module.handler;

import lombok.Getter;

@Getter
public class MyInternalServerException extends RuntimeException {

    private final String status="400";
    public MyInternalServerException(String message) {
        super(message);
    }
}