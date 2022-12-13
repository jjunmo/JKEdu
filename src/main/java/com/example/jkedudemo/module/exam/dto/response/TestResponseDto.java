package com.example.jkedudemo.module.exam.dto.response;

import com.example.jkedudemo.module.exam.entity.ExamPaper;
import com.example.jkedudemo.module.member.dto.response.MemberStatusOkResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestResponseDto {
    private String status;
    private String message;
    private Long answerPaperId;

    public static TestResponseDto statusOk(ExamPaper examPaper){
        return TestResponseDto.builder()
                .status("200")
                .message("OK")
                .answerPaperId(examPaper.getId())
                .build();
    }
}
