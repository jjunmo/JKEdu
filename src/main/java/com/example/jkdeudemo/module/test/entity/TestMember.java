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

    @OneToOne
    private Students student;

    @OneToMany
    private List<ExamCategory> examCategorys = new ArrayList<>();

}
