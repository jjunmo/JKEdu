package com.example.jkedudemo.module.exam.dto.response;

import com.example.jkedudemo.module.exam.dto.ExamQuestDTO;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamRefreshResponseDto {
    private String status;

    private String message;

    private String number;

    private ExamQuestDTO examQuestDTO;

    public static ExamRefreshResponseDto examDTO(ExamQuestDTO examQuestDTO,Long number){
        return ExamRefreshResponseDto.builder()
                .status("200")
                .message("OK")
                .number(String.valueOf(number))
                .examQuestDTO(examQuestDTO)
                .build();
    }
}
