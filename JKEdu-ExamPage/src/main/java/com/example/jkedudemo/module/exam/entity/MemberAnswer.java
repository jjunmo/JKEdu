package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.enums.YN;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "MEMBER_EXAM_ANSWER")
public class MemberAnswer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_EXAM_ANSWER_CATEGORY")
    @Comment("시험 본 문제")
    private MemberAnswerCategory memberAnswerCategory;

    //문제
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EXAM_QUEST")
    @Comment("시험문제")
    private ExamQuest examQuest;

    //내가 입력한 답
    @Comment("내가 입력한 답")
    private String myAnswer;

    // 정답 여부
    @Enumerated(EnumType.STRING)
    @Comment("정답 여부")
    private YN answerYN;

}
