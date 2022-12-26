package com.example.jkedudemo.module.exam.dto.response;

import com.example.jkedudemo.module.common.enums.exam.Exam;
import com.example.jkedudemo.module.exam.entity.ExamResult;
import lombok.*;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamResultCheckResponseDto {
    String status;
    String message;
    List<Exam> examList;

    public static ExamResultCheckResponseDto start(List<Exam> examResult){
        return ExamResultCheckResponseDto.builder()
                .status("200")
                .message("OK")
                .examList(examResult)
                .build();
    }

}
