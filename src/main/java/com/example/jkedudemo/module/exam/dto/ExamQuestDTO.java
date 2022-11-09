package com.example.jkedudemo.module.exam.dto;

import com.example.jkedudemo.module.common.enums.Quest;
import lombok.Data;

import java.util.List;

@Data
public class ExamQuestDTO {
    private Long id;
    private Quest type;
    private String question;
    private String subQuestion;
    private String imgUrl;
    private String videoUrl;
    private String speakUrl;

    // 객관식일때만 있음
    List<ExamMultipleChoiceDTO> MultipleChoice;

}
