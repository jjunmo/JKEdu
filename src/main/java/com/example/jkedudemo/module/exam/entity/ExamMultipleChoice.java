package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.util.BaseTime;
import com.example.jkedudemo.module.exam.dto.ExamMultipleChoiceDTO;
import com.example.jkedudemo.module.exam.dto.ExamQuestDTO;
import lombok.*;

import javax.persistence.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "EXAM_MULTIPLE_CHOICE")
public class ExamMultipleChoice extends BaseTime {
    @Id
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EXAM_QUEST")
    private ExamQuest quest;
    //문항
    private Integer questNumber;

    private String questContent;

//    public ExamMultipleChoiceDTO entityToDto () {
//        ExamMultipleChoiceDTO examMultipleChoiceDTO=new ExamMultipleChoiceDTO();
//        examMultipleChoiceDTO.setQuestNumber(this.getQuestNumber());
//        examMultipleChoiceDTO.setQuestContent(this.getQuestContent());
//        return examMultipleChoiceDTO;
//    }

}
