package com.example.jkedudemo.module.exam.dto;

import com.example.jkedudemo.module.common.enums.QuestType;
import lombok.Data;

import java.util.List;

@Data
public class ExamQuestDTO {
    private Long id;
    private QuestType type;
    private String title;
    private String subTitle;
    private String imgUrl;
    private String videoUrl;
    private String speakUrl;

    // 객관식일때만 있음
    List<Exam객관식DTO> 객관식문항;

}
