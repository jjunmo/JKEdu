package com.example.jkedudemo.module.academy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AcademyTestMember {

    @Id
    private Long id;

    private String name;

    private String birth;

    private String phone_number;

    private String answer;

    private Integer ok_answer;
}
