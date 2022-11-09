package com.example.jkedudemo.module.exam.dto;

import com.example.jkedudemo.module.common.enums.Exam;
import lombok.Data;

import java.util.List;

@Data
public class ExamCategoryDTO {
    private Long id;

    private Exam exam;

    private List<ExamQuestDTO> examQuestDTOList;

}

//JSON
//{
//    id: 1
//    EXAM: SPEAKING ,READING ,LISTENING, GRAMMAR, WRITING
//    memberExamQuestDTOList: [
//        {
//            id:1
//            quest : DESCRIPTIVE,MULTIPLE
//            question :
//            subQuestion :
//            imgUrl :
//            videoUrl :
//            speakUrl :
//        },
//        {
//            id:2
//            questType : DESCRIPTIVE,MULTIPLE
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

