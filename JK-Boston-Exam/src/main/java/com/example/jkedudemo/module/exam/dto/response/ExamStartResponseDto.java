package com.example.jkedudemo.module.exam.dto.response;

import com.example.jkedudemo.module.exam.entity.ExamResult;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamStartResponseDto {
    private String status;

    private String message;

    private Long examId;

    public static ExamStartResponseDto start(ExamResult examResult){
        return ExamStartResponseDto.builder()
                .status("200")
                .message("OK")
                .examId(examResult.getId())
                .build();
    }
}
