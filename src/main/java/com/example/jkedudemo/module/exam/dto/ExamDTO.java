package com.example.jkedudemo.module.exam.dto;

import com.example.jkedudemo.module.common.enums.exam.Exam;
import lombok.Data;

@Data
public class ExamDTO {
    private Long id;

    private Exam exam;

    private ExamQuestDTO examQuestDTO;

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

