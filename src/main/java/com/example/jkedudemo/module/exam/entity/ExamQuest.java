package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.util.BaseTime;
import com.example.jkedudemo.module.common.enums.Quest;

import javax.persistence.*;

@Entity(name = "MEMBER_EXAM_QUEST")
public class ExamQuest extends BaseTime {
    @Id
    private Long id;

    //시험 유형
    @ManyToOne
    private ExamCategory category;

    //주관식 , 객관식 DESCRIPTIVE_FORM, MULTIPLECHOICE
    @Enumerated(EnumType.STRING)
    private Quest type;

    //해당문제의 정답
    private String rightAnswer;

    // 질문
    private String question;
    //부가 질문
    private String subQuestion;
    //img URL
    private String imgUrl;
    //video URL
    private String videoUrl;
    //음성파일 URL
    private String speakUrl;

}
