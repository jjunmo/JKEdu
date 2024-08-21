package com.example.jkedudemo.module.member.dto.response;

import com.example.jkedudemo.module.exam.entity.ExamResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
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