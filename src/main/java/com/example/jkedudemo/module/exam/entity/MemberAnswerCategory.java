package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.Util.BaseTime;
import com.example.jkedudemo.module.member.entity.Member;

import javax.persistence.*;

@Entity(name = "MEMBER_EXAM_ANSWER_CATEGORY")
public class MemberAnswerCategory extends BaseTime {
    @Id
    private Long id;

    //멤버 정보
    @ManyToOne
    private Member member;

    //문제 유형
    @ManyToOne
    private ExamCategory category;

}
