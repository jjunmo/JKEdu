package com.example.jkedudemo.module.exam.repository;

import com.example.jkedudemo.module.exam.entity.ExamCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamCategoryRepository extends JpaRepository<ExamCategory,Long> {
}
