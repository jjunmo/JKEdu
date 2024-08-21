package com.example.jkedudemo.module.handler;

import lombok.Getter;

@Getter
public class MyUnAuthorizedException2 extends RuntimeException{

    public MyUnAuthorizedException2(String message) {
        super(message);
    }
}
