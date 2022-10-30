package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.BaseTime;
import com.example.jkedudemo.module.common.enums.QuestType;
import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.member.entity.Member;

import javax.persistence.*;

@Entity(name = "MEMBER_EXAM_ANSWER")
public class MemberAnswer extends BaseTime {
    @Id
    private Long id;


    @ManyToOne
    private MemberAnswerCategory category;

    @ManyToOne
    private ExamQuest quest;

    private String 나의답;

    @Enumerated(EnumType.STRING)
    private YN 정답;

}
