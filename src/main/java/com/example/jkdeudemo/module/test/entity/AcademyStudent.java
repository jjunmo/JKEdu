package com.example.jkdeudemo.module.test.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AcademyStudent {

    @Id
    private Long id;

    private String name;

    private String birth;

    private String phoneNumber;
}
