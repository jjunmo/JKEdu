package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.BaseTime;
import com.example.jkedudemo.module.common.enums.ExamType;

import javax.persistence.*;

@Entity(name = "MEMBER_EXAM_CATEGORY")
public class ExamCategory extends BaseTime {
    @Id
    private Long id;

//    @OneToMany
//    private List<SpeakingTest> speakingTestList=new ArrayList<>();

    //시험시 쓰기문제는 하나
//    @OneToOne
//    private WritingTest writingTest;
//
//    @OneToMany
//    private List<ReadingTest> readingTests=new ArrayList<>();
//
//    @OneToMany
//    private List<ListeningTest> listeningTests=new ArrayList<>();
//
//    @OneToMany
//    private List<GrammerTest> grammerTests=new ArrayList<>();



    /**
     * 시험 응시자
     */

    @Enumerated(EnumType.STRING)
    private ExamType examType;


}
