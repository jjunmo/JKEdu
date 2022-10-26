package com.example.jkdeudemo.module.exam.entity;

import com.example.jkdeudemo.module.exam.entity.category.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ExamCategory {
    @Id
    private Long id;

    @OneToMany
    private List<SpeakingTest> speakingTestList=new ArrayList<>();

    @OneToOne
    private WritingTest writingTest;

    @OneToMany
    private List<ReadingTest> readingTests=new ArrayList<>();

    @OneToMany
    private List<ListeningTest> listeningTests=new ArrayList<>();

    @OneToMany
    private List<GrammerTest> grammerTests=new ArrayList<>();



}
