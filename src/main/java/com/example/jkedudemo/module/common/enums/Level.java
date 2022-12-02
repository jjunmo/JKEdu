package com.example.jkedudemo.module.common.enums;

public enum Level {
    //문제 등급
    //멤버 등급
    PRE_A1(2.5,200),
    A1(7.5, 400),
    A2(25, 600),
    B1(45, 800),
    B2(65, 1000),
    C(85, 1200);

    private final double value;
    private final int cut;

    Level(double value, int cut) {
        this.value = value;
        this.cut = cut;
    }

    public int getValue() { return Integer.parseInt(String.valueOf(value)); }

    public int getCut(){return cut;}

    public Level getLevel(double a){
        if(a<=Level.PRE_A1.getCut()){
            return PRE_A1;
        } else if (a<=Level.A1.getCut()) {
            return A1;
        }else if (a<=Level.A2.getCut()) {
            return A2;
        }else if (a<=Level.B1.getCut()) {
            return B1;
        }else if (a<=Level.B2.getCut()) {
            return B2;
        }else return C;
    }

    // 2.5 /7.5/25/45/65/85

    // 4% / 522 / 913.5 / 1305 / 1696.5 /
}