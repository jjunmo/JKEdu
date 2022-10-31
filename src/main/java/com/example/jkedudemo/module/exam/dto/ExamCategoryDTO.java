package com.example.jkedudemo.module.exam.dto;

import com.example.jkedudemo.module.common.enums.ExamType;
import lombok.Data;

import java.util.List;

@Data
public class ExamCategoryDTO {
    private Long id;

    private ExamType examType;


    private List<ExamQuestDTO> examQuestDTOList;

}

//JSON
//{
//    id: 1
//    examType: "Speaking",
//    memberExamQuestDTOList: [
//        {
//            id:1
//            questType : 주
//        },
//        {
//            id:2
//            questType : 객
//            객관식문항 : [
//                {
//                    sort: 1,
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

