package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.BaseTime;

import javax.persistence.*;

@Entity(name = "MEMBER_EXAM_QUEST_MULTIPLE_CHOICE")
public class ExamMultipleChoice extends BaseTime {
    @Id
    private Long id;

    //
    @ManyToOne
    private ExamQuest quest;
    //문항
    private Integer questNumber;

    private String questContent;

}
