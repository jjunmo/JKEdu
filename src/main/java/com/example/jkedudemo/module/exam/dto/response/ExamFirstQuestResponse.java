package com.example.jkedudemo.module.exam.dto.response;

import com.example.jkedudemo.module.exam.dto.ExamQuestDTO;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamFirstQuestResponse {

    private String status;

    private String message;

    private int number;

    private ExamQuestDTO examQuestDTO;



    public static ExamFirstQuestResponse examDTO(ExamQuestDTO examQuestDTO, int number){
        return ExamFirstQuestResponse.builder()
                .status("200")
                .message("OK")
                .number(number)
                .examQuestDTO(examQuestDTO)
                .build();
    }

}

//JSON
//{
//    status : 200
//    message : OK
//    examQuestDTO
//        {
//            id:1
//            quest : DESCRIPTIVE
//            question : 문제
//            subQuestion : 부제
//            imgUrl :
//            videoUrl :
//            speakUrl :
//            level : PRE_A1,A1,A2,B1,B2,C1
//        }
//
//        {
//            id:1
//            quest : MULTIPLE
//            question : 문제
//            subQuestion : 부제
//            imgUrl :
//            videoUrl :
//            speakUrl :
//            level : PRE_A1,A1,A2,B1,B2,C1
//
//            MULTIPLE_CHODICE : [
//                {
//                    questNumber: 1,
//                    questContent: flisajelf
//                },
//                {
//                    questNumber: 2,
//                    questContent: asljeflsejf
//                }
//            ]
//        }
//    ]
//}

