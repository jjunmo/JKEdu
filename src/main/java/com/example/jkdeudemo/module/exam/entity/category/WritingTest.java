package com.example.jkdeudemo.module.exam.entity.category;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class WritingTest {

    @Id
    private Long id;

    private String question;

    private String correct_answer;

}
