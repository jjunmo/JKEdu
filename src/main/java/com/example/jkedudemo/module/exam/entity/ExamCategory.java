package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.enums.exam.Exam;
import com.example.jkedudemo.module.common.util.BaseTime;

import javax.persistence.*;

@Entity(name = "MEMBER_EXAM_CATEGORY")
public class ExamCategory extends BaseTime {
    @Id
    private Long id;

    //시험 유형
    @Enumerated(EnumType.STRING)
    private Exam exam;

}
