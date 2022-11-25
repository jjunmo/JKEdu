package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.enums.Level;
import com.example.jkedudemo.module.common.util.BaseTime;
import com.example.jkedudemo.module.common.enums.exam.Quest;
import com.example.jkedudemo.module.exam.dto.ExamMultipleChoiceDTO;
import com.example.jkedudemo.module.exam.dto.ExamQuestDTO;
import com.example.jkedudemo.module.exam.dto.response.ExamFirstQuestResponse;
import com.example.jkedudemo.module.exam.repository.ExamCategoryRepository;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "EXAM_QUEST")
public class ExamQuest {
    @Id
    private Long id;

    //시험 유형
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "EXAM_CATEGORY")
    private ExamCategory examCategory;

    //주관식 , 객관식 DESCRIPTIVE, MULTIPLE
    @Enumerated(EnumType.STRING)
    @Column(name = "QUEST")
    private Quest quest;

    @Enumerated(EnumType.STRING)
    @Column(name = "LEVEL")
    private Level level;

    //해당문제의 정답
    @Column(name = "RIGHTANSWER")
    private String rightAnswer;

    // 질문
    @Column(name = "QUESTION")
    private String question;
    //부가 질문
    @Column(name = "SUBQUESTION")
    private String subQuestion;
    //img URL
    private String imgUrl;
    //video URL
    private String videoUrl;
    //음성파일 URL
    private String speakUrl;

    public ExamQuest(String id, ExamCategory examCategory, String quest,  String question, String subQuestion,String rightAnswer,String imgUrl, String videoUrl, String speakUrl, String level) {
        this.id=Long.parseLong(id);
        this.examCategory=examCategory;
        this.quest= Quest.valueOf(quest);
        this.question=question;
        this.subQuestion=subQuestion;
        this.rightAnswer=rightAnswer;
        this.imgUrl=imgUrl;
        this.videoUrl=videoUrl;
        this.speakUrl=speakUrl;
        this.level=Level.valueOf(level);
    }

    public ExamQuestDTO entityToMultipleDto (List<ExamMultipleChoice> examMultipleChoiceList) {
        ExamQuestDTO examQuestDTO=new ExamQuestDTO();

        examQuestDTO.setId(this.id);
        examQuestDTO.setQuest(this.quest);
        examQuestDTO.setLevel(this.level);
        examQuestDTO.setQuestion(this.question);
        examQuestDTO.setSubQuestion(this.subQuestion);
        examQuestDTO.setImgUrl(this.imgUrl);
        examQuestDTO.setVideoUrl(this.videoUrl);
        examQuestDTO.setSpeakUrl(this.speakUrl);

        List<ExamMultipleChoiceDTO> examMultipleChoiceDTOList = new ArrayList<>();

        examMultipleChoiceList.forEach(
                examMultipleChoice -> {
                    ExamMultipleChoiceDTO examMultipleChoiceDTO = new ExamMultipleChoiceDTO();
                    examMultipleChoiceDTO.setQuestNumber(examMultipleChoice.getQuestNumber());
                    examMultipleChoiceDTO.setQuestContent(examMultipleChoice.getQuestContent());
                    examMultipleChoiceDTOList.add(examMultipleChoiceDTO);
                }
        );
                examQuestDTO.setMultipleChoice(examMultipleChoiceDTOList);
        return examQuestDTO;
    }

    public ExamQuestDTO entityToDto () {
        ExamQuestDTO examQuestDTO=new ExamQuestDTO();
        examQuestDTO.setId(id);
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
