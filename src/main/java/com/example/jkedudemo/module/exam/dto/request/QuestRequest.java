package com.example.jkedudemo.module.exam.dto.request;

import com.example.jkedudemo.module.common.enums.exam.Exam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuestRequest {
    private Exam exam;
}
