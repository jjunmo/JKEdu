package com.example.jkdeudemo.module.exam.repository;

import com.example.jkdeudemo.module.exam.entity.ExamCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<ExamCategory, Long> {
}
