package com.example.jkedudemo.module.exam.dto;

import com.example.jkedudemo.module.common.BaseTime;
import lombok.Data;

@Data
public class Exam객관식DTO extends BaseTime {
    private Long id;
    private Integer sort;
    private String title;
}
