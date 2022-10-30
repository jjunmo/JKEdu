package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.BaseTime;

import javax.persistence.*;

@Entity(name = "MEMBER_EXAM_QUEST_객관식")
public class Exam객관식 extends BaseTime {
    @Id
    private Long id;

    /**
     * 시험 응시자
     */
    @ManyToOne
    private ExamQuest quest;

    private Integer sort;
    private String title;

}
