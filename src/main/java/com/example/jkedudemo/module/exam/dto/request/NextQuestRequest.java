package com.example.jkedudemo.module.exam.dto.request;

import com.example.jkedudemo.module.common.enums.exam.Exam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NextQuestRequest {

    private Long studentId;

    private Long examId;

    private Long examPaperId;

    private String myAnswer;

    private String number;

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer.toUpperCase();
    }
    public String getMyAnswer() {
        return myAnswer;
    }

}
