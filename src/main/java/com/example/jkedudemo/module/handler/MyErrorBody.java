package com.example.jkedudemo.module.handler;

import lombok.Getter;

@Getter
public class MyErrorBody {

    private final String status;
    private final String message;

    public MyErrorBody(String status) {
        this.status = status;
        this.message = "";
    }

    public MyErrorBody(String message, String status) {
        this.message = message;
        this.status = status;
    }
}
