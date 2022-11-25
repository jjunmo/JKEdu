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

    private String status;

    private String message;

    private String number;

    private ExamQuestDTO examQuestDTO;

    public static ExamNextQuestResponse examDTO(ExamQuestDTO examQuestDTO,String number){
        return ExamNextQuestResponse.builder()
                .status("200")
                .message("OK")
                .number(Integer.toString(Integer.parseInt(number)+1))
                .examQuestDTO(examQuestDTO)
                .build();
    }

    public static ExamNextQuestResponse examDTO2(){
        return ExamNextQuestResponse.builder()
                .status("200")
                .message("END")
                .build();
    }

}
