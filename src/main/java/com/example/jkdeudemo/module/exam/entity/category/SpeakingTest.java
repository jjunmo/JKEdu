package com.example.jkdeudemo.module.exam.entity.category;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SpeakingTest {

    @Id
    private Long id;

    private String question;
}
