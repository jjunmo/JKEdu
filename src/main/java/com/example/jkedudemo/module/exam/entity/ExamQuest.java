package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.enums.Level;
import com.example.jkedudemo.module.common.util.BaseTime;
import com.example.jkedudemo.module.common.enums.exam.Quest;
import com.example.jkedudemo.module.exam.dto.ExamDTO;
import com.example.jkedudemo.module.exam.dto.ExamMultipleChoiceDTO;
import com.example.jkedudemo.module.exam.dto.ExamQuestDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "EXAM_QUEST")
public class ExamQuest extends BaseTime {
    @Id
    private Long id;

    //시험 유형
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_EXAM_CATEGORY")
    private ExamCategory examCategory;

    //주관식 , 객관식 DESCRIPTIVE, MULTIPLE
    @Enumerated(EnumType.STRING)
    private Quest quest;

    @Enumerated(EnumType.STRING)
    private Level level;

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

    public ExamQuestDTO entityToMultipleDto (List<ExamMultipleChoiceDTO> examMultipleChoiceList) {
        ExamQuestDTO examQuestDTO=new ExamQuestDTO();
        examQuestDTO.setMultipleChoice(examMultipleChoiceList);
        examQuestDTO.setQuest(this.quest);
        examQuestDTO.setLevel(this.level);
        examQuestDTO.setQuestion(this.question);
        examQuestDTO.setSubQuestion(this.subQuestion);
        examQuestDTO.setImgUrl(this.imgUrl);
        examQuestDTO.setVideoUrl(this.videoUrl);
        examQuestDTO.setSpeakUrl(this.speakUrl);
        return examQuestDTO;
    }

    public ExamQuestDTO entityToDto () {
        ExamQuestDTO examQuestDTO=new ExamQuestDTO();
        examQuestDTO.setQuest(this.quest);
        examQuestDTO.setLevel(this.level);
        examQuestDTO.setQuestion(this.question);
        examQuestDTO.setSubQuestion(this.subQuestion);
        examQuestDTO.setImgUrl(this.imgUrl);
        examQuestDTO.setVideoUrl(this.videoUrl);
        examQuestDTO.setSpeakUrl(this.speakUrl);
        return examQuestDTO;
    }


}
