package com.example.jkedudemo.module.exam.dto;

import com.example.jkedudemo.module.common.util.BaseTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ExamMultipleChoiceDTO extends BaseTime {
    private Long id;
    //문항
    private Integer questNumber;
    private String questContent;
}
