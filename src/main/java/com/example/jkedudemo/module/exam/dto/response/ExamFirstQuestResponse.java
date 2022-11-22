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
    private Long id;

    private Exam exam;

    private ExamQuestDTO examQuestDTO;

    private String status;

    private String message;


    public static ExamFirstQuestResponse examDTO(ExamQuestDTO examQuestDTO, Exam exam){
        return ExamFirstQuestResponse.builder()
                .exam(exam)
                .status("200")
                .message("OK")
                .examQuestDTO(examQuestDTO)
                .build();
    }

}

//JSON
//{
//    id: 1
//    EXAM: SPEAKING ,READING ,LISTENING, GRAMMAR, WRITING
//    ExamQuest
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

