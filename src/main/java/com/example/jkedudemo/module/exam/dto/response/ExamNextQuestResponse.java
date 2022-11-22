package com.example.jkedudemo.module.exam.dto.response;

import com.example.jkedudemo.module.common.enums.exam.Exam;
import com.example.jkedudemo.module.exam.dto.ExamQuestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamNextQuestResponse {
    private Long id;

    private Exam exam;

    private ExamQuestDTO examQuestDTO;

    private String status;

    private String message;
}
