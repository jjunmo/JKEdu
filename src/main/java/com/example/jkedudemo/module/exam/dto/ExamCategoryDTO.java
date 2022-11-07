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
//    examType: "Speaking",
//    memberExamQuestDTOList: [
//        {
//            id:1
//            questType : MULTIPLE_CHODICE , DESCRIPTIVE_FORM
//        },
//        {
//            id:2
//            questType : MULTIPLE_CHODICE , DESCRIPTIVE_FORM
//            MULTIPLE_CHODICE : [
//                {
//                    quest: 1,
//                    title: flisajelf
//                },
//                {
//                    sort: 2,
//                    title: asljeflsejf
//                }
//            ]
//        }
//    ]
//}

