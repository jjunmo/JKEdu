package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.enums.exam.Exam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity(name = "EXAM_CATEGORY")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ExamCategory {

    @Id
    private Long id;

    //시험 유형
    @Enumerated(EnumType.STRING)
    @Comment("시험 유형")
    private Exam exam;

    public ExamCategory(String id, String exam) {
        this.id=Long.parseLong(id);
        this.exam= Exam.valueOf(exam);
    }

}
