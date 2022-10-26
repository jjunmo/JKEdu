package com.example.jkdeudemo.module.member.entity.student;

import com.example.jkdeudemo.module.member.entity.member.StudentMember;
import com.example.jkdeudemo.module.test.entity.AcademyStudent;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Students {

    @Id
    private Long id;

    @ManyToMany
    private List<StudentMember> studentmembers = new ArrayList<>();

    @ManyToMany
    private List<AcademyStudent> academyStudents = new ArrayList<>();


}
