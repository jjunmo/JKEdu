package com.example.jkedudemo.module.member.entity;

import com.example.jkedudemo.module.member.entity.Member;

import javax.persistence.*;

@Entity
public class TestMember {

    @Id
    private Long id;

    /**
     * 학생 정보
     */
    @OneToOne
    private Member member;

    /**
     * 작성한 답안
     */
    private String answer;

    /**
     * 정답 갯수
     */
    private Integer ok_answer;

}
