package com.example.jkedudemo.module.member.dto.response;

import com.example.jkedudemo.module.exam.entity.ExamResult;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultListDto {
    Long examId;
    LocalDate examDate;

    public static ResultListDto getResultList (ExamResult examResult){
        return ResultListDto.builder()
                .examId(examResult.getId())
                .examDate(examResult.getCreateDate())
                .build();
    }
}