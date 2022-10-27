package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.exam.entity.category.*;
import com.example.jkedudemo.module.test.entity.TestMember;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ExamCategory {
    @Id
    private Long id;

    @OneToMany
    private List<SpeakingTest> speakingTestList=new ArrayList<>();

    //시험시 쓰기문제는 하나
    @OneToOne
    private WritingTest writingTest;

    @OneToMany
    private List<ReadingTest> readingTests=new ArrayList<>();

    @OneToMany
    private List<ListeningTest> listeningTests=new ArrayList<>();

    @OneToMany
    private List<GrammerTest> grammerTests=new ArrayList<>();

    /**
     * 시험 응시자
     */
    @ManyToOne
    private TestMember test_member;



}
