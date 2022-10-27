package com.example.jkdeudemo.module.test.entity;

import com.example.jkdeudemo.module.exam.entity.ExamCategory;
import com.example.jkdeudemo.module.member.entity.member.AcademyMember;
import com.example.jkdeudemo.module.member.entity.member.StudentMember;
import com.example.jkdeudemo.module.member.entity.student.Students;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TestMember {

    @Id
    private Long id;

    /**
     * 학생 정보
     */


    /**
     * 작성한 답안
     */
    private String answer;

    /**
     * 정답 갯수
     */
    private Integer ok_answer;

}
