package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.util.BaseTime;
import com.example.jkedudemo.module.common.enums.YN;

import javax.persistence.*;

@Entity(name = "MEMBER_EXAM_ANSWER")
public class MemberAnswer extends BaseTime {
    @Id
    private Long id;


    @ManyToOne
    private MemberAnswerCategory memberAnswerCategory;

    //문제
    @ManyToOne
    private ExamQuest examQuest;

    //내가 입력한 답
    private String myAnswer;

    // 정답 여부
    @Enumerated(EnumType.STRING)
    private YN AnswerYN;

}
