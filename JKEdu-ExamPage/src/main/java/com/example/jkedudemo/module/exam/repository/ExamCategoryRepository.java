package com.example.jkedudemo.module.exam.repository;

import com.example.jkedudemo.module.common.enums.exam.Exam;
import com.example.jkedudemo.module.exam.entity.ExamCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamCategoryRepository extends JpaRepository<ExamCategory,Long> {

    Optional<ExamCategory> findByExam(Exam exam);
}
