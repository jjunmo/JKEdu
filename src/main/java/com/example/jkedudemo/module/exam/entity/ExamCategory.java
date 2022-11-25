package com.example.jkedudemo.module.exam.entity;

import com.example.jkedudemo.module.common.enums.exam.Exam;
import com.example.jkedudemo.module.common.util.BaseTime;
import com.example.jkedudemo.module.exam.repository.ExamCategoryRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

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
    private Exam exam;

    public ExamCategory(String id, String exam) {
        this.id=Long.parseLong(id);
        this.exam= Exam.valueOf(exam);
    }

}
