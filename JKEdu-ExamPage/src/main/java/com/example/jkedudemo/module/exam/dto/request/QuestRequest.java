package com.example.jkedudemo.module.exam.dto.request;

import com.example.jkedudemo.module.common.enums.exam.Exam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuestRequest {
    private Exam exam;

    private Long examPaperId;


    public void setExam(String exam) {
        this.exam = Exam.valueOf(exam.toUpperCase());
    }
    public Exam getExam() {
        return exam;
    }
}
