package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.enums.Level;
import com.example.jkedudemo.module.common.enums.exam.Quest;
import com.example.jkedudemo.module.exam.dto.ExamMultipleChoiceDTO;
import com.example.jkedudemo.module.exam.dto.ExamQuestDTO;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @Comment("문제 영역")
    private ExamCategory examCategory;
    //주관식 , 객관식 DESCRIPTIVE, MULTIPLE
    @Enumerated(EnumType.STRING)
    @Column(name = "QUEST")
    @Comment("문제 유형")
    private Quest quest;
    // 질문
    @Lob
    @Column(name = "QUESTION",columnDefinition = "LONGTEXT")
    @Comment("문제")
    private String question;
    //부가 질문
    @Column(name = "SUBQUESTION",columnDefinition = "LONGTEXT")
    @Comment("부제")
    private String subQuestion;
    //해당문제의 정답
    @Column(name = "RIGHTANSWER",columnDefinition = "LONGTEXT")
    @Comment("정답")
    private String rightAnswer;
    //img URL
    @Comment("이미지")
    private String imgUrl;
    //video URL
    @Comment("비디오")
    private String videoUrl;
    //음성파일 URL
    @Comment("음성")
    private String speakUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "LEVEL")
    @Comment("문제 난이도")
    private Level level;

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
