package com.example.jkedudemo.module.exam.dto.response;

import com.example.jkedudemo.module.exam.dto.ExamQuestDTO;
import com.example.jkedudemo.module.exam.entity.ExamPaper;
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

    private Long examPaper;

    private Long studentId;

    private String number;

    private ExamQuestDTO examQuestDTO;

    public static ExamNextQuestResponse examDTO(ExamQuestDTO examQuestDTO, ExamPaper examPaper, String number, Long studentId){
        return ExamNextQuestResponse.builder()
                .status("200")
                .message("NEXT")
                .examPaper(examPaper.getId())
                .studentId(studentId)
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
