package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.utility.BaseTime;
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
@Table(name = "MEMBER_EXAM_RESULT")
public class ExamResult extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER")
    @Comment("응시자")
    private Member member;

}
