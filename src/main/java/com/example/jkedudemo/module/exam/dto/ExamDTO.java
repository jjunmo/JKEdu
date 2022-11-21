package com.example.jkedudemo.module.exam.dto;

import com.example.jkedudemo.module.common.enums.exam.Exam;
import com.example.jkedudemo.module.member.dto.response.MemberNameResopnseDto;
import com.example.jkedudemo.module.member.entity.Member;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamDTO {
    private Long id;

    private Exam exam;

    private ExamQuestDTO examQuestDTO;

    public static ExamDTO examDTO(ExamQuestDTO examQuestDTO,Exam exam){
        return ExamDTO.builder()
                .exam(exam)
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

