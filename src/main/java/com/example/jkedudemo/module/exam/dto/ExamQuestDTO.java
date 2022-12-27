package com.example.jkedudemo.module.exam.dto;

import com.example.jkedudemo.module.common.enums.Level;
import com.example.jkedudemo.module.common.enums.exam.Quest;
import com.example.jkedudemo.module.exam.entity.ExamMultipleChoice;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExamQuestDTO {
    private Long id;
    private Quest quest;
    private String question;
    private String subQuestion;
    private String imgUrl;
    private String videoUrl;
    private String speakUrl;
    private Level level;

    // 객관식일때만 있음
    List<ExamMultipleChoiceDTO> MultipleChoice;



}
