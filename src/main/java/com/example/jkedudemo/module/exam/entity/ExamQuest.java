package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.BaseTime;
import com.example.jkedudemo.module.common.enums.QuestType;

import javax.persistence.*;

@Entity(name = "MEMBER_EXAM_QUEST")
public class ExamQuest extends BaseTime {
    @Id
    private Long id;

    //시험 유형
    @ManyToOne
    private ExamCategory category;

    //객관식 , 주관식
    @Enumerated(EnumType.STRING)
    private QuestType type;

    private String 정답;

    private String title;
    private String subTitle;
    private String imgUrl;
    private String videoUrl;
    private String speakUrl;

}
