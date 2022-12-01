package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.enums.Level;
import com.example.jkedudemo.module.common.util.BaseTime;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "MEMBER_EXAM_RESULT")
public class ExamPaper extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Level level;

}
