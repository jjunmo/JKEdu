package com.example.jkedudemo.module.handler;

import lombok.Getter;

@Getter
public class MyErrorBody {

    private String errortype;
    private final String status;
    private final String message;


    public MyErrorBody(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public MyErrorBody(String errortype,String status, String message) {
        this.errortype= errortype;
        this.status = status;
        this.message = message;

    }
}
