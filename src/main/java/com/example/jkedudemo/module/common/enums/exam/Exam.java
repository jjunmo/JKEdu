package com.example.jkedudemo.module.common.enums.exam;

public enum Exam {
    //ExamCategory
    SPEAKING(34) ,READING(34) ,LISTENING(34), GRAMMAR(34), WRITING(34);

    private final int value;

    Exam(int value) { this.value = value; }

    public int getValue() { return value; }
}
