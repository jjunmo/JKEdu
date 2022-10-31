package com.example.jkedudemo.module.exam.dto;

import com.example.jkedudemo.module.common.BaseTime;
import lombok.Data;

@Data
public class ExamMultipleChoiceDTO extends BaseTime {
    private Long id;
    //문항
    private Integer questNumber;
    private String questContent;
}
