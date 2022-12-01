package com.example.jkedudemo.module.exam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NextQuestRequest {

    private Long studentId;

    private Long examId;

    private Long examPaper;

    private String myAnswer;

    private String number;

}
