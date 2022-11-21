package com.example.jkedudemo.module.exam.dto.response;

import com.example.jkedudemo.module.common.enums.exam.Quest;
import com.example.jkedudemo.module.exam.entity.ExamCategory;
import com.example.jkedudemo.module.exam.entity.ExamQuest;
import com.example.jkedudemo.module.member.dto.response.MemberNameResopnseDto;
import com.example.jkedudemo.module.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamQuestResponse {

    public ExamCategory examCategory;
    public Quest quest;
    public String question;
    public String subQuestion;
    public String imgUrl;
    public String videoUrl;
    public String speakUrl;

//    public static ExamQuestResponse examQuestResponse(ExamCategory examCategory){
//        return ExamQuestResponse.builder()
//                .examCategory(examCategory.getExam())
//                .quest(examQuset.getQuest())
//                .question(examQuset.getQuestion())
//                .subQuestion(examQuset.getSubQuestion())
//                .imgUrl(examQuset.getImgUrl())
//                .videoUrl(examQuset.getVideoUrl())
//                .speakUrl(examQuset.getSpeakUrl())
//                .build();
//    }
}
