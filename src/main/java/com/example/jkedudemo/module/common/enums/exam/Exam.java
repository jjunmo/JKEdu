package com.example.jkedudemo.module.common.enums.exam;

public enum Exam {
    //ExamCategory
    //TODO: 문제 개수 이후 수정
    SPEAKING(5) ,READING(5) ,LISTENING(5), GRAMMAR(5), WRITING(5);

    private final int value;

    Exam(int value) { this.value = value; }

    public int getValue() { return value; }
}
