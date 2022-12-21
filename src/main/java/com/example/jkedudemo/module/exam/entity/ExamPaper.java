package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.enums.Level;
import com.example.jkedudemo.module.common.enums.exam.Exam;
import com.example.jkedudemo.module.common.enums.member.Role;
import com.example.jkedudemo.module.common.util.BaseTime;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "MEMBER_EXAM_PAPER")
public class ExamPaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Enumerated(EnumType.STRING)
    private Exam examCategory;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_EXAM_RESULT")
    private ExamResult examResult;

}
