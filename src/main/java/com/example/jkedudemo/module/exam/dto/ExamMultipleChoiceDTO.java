package com.example.jkedudemo.module.exam.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ExamMultipleChoiceDTO{
    //문항
    private Integer questNumber;
    private String questContent;
}
