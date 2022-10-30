package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.BaseTime;
import com.example.jkedudemo.module.common.enums.QuestType;
import com.example.jkedudemo.module.member.entity.Member;

import javax.persistence.*;

@Entity(name = "MEMBER_EXAM_ANSWER_CATEGORY")
public class MemberAnswerCategory extends BaseTime {
    @Id
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private ExamCategory category;

}
