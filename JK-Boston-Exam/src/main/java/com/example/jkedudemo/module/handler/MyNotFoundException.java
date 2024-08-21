package com.example.jkedudemo.module.handler;

import lombok.Getter;

@Getter
public class MyNotFoundException extends RuntimeException {
    public MyNotFoundException(String message) {super(message);}
}
