package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.util.BaseTime;
import com.example.jkedudemo.module.exam.dto.ExamMultipleChoiceDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "EXAM_MULTIPLE_CHOICE")
public class ExamMultipleChoice{
    @Id
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "EXAM_QUEST")
    private ExamQuest quest;
    //문항
    private Integer questNumber;

    private String questContent;

    public ExamMultipleChoice(String id, ExamQuest quest, String questNumber, String questContent) {
        this.id=Long.parseLong(id);
        this.quest=quest;
        this.questNumber=Integer.parseInt(questNumber);
        this.questContent=questContent;
    }
}
