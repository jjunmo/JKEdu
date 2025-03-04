package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.member.entity.Member;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "MEMBER_EXAM_ANSWER_CATEGORY")
public class MemberAnswerCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //멤버 정보
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER")
    @Comment("시험 응시자")
    private Member member;

    //문제 유형
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EXAM_CATEGORY")
    @Comment("시험 문제 영역")
    private ExamCategory examCategory;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_EXAM_PAPER")
    @Comment("시험지")
    private ExamPaper examPaper;

}
