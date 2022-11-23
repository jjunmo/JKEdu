package com.example.jkedudemo.module.exam.dto.response;

import com.example.jkedudemo.module.common.enums.exam.Exam;
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

    private ExamQuestDTO examQuestDTO;



    public static ExamFirstQuestResponse examDTO(ExamQuestDTO examQuestDTO){
        return ExamFirstQuestResponse.builder()
                .status("200")
                .message("OK")
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
//            question :
//            subQuestion :
//            imgUrl :
//            videoUrl :
//            speakUrl :
//            level :
//        }

//        {
//            id:1
//            quest : MultipleChoice
//            question :
//            subQuestion :
//            imgUrl :
//            videoUrl :
//            speakUrl :
//            level :
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

