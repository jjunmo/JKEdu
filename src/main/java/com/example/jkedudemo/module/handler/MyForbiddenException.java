package com.example.jkedudemo.module.handler;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.Getter;

@Getter
public class MyForbiddenException extends RuntimeException {

    private final String status="403";
    public MyForbiddenException(String message) {
        super(message);
    }
}

