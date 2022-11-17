package com.example.jkedudemo.module.exam.repository;

import com.example.jkedudemo.module.exam.entity.ExamCategory;
import com.example.jkedudemo.module.exam.entity.ExamQuest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamCategoryRepository extends JpaRepository<ExamCategory,Long> {
}
