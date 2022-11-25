package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.util.BaseTime;
import com.example.jkedudemo.module.common.enums.YN;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "MEMBER_EXAM_ANSWER")
public class MemberAnswer extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_EXAM_ANSWER_CATEGORY")
    private MemberAnswerCategory memberAnswerCategory;

    //문제
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EXAM_QUEST")
    private ExamQuest examQuest;

    //내가 입력한 답
    private String myAnswer;

    // 정답 여부
    @Enumerated(EnumType.STRING)
    private YN AnswerYN;

}
