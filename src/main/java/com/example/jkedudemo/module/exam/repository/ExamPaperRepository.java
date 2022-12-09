package com.example.jkedudemo.module.exam.repository;

import com.example.jkedudemo.module.exam.entity.ExamPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface ExamPaperRepository extends JpaRepository<ExamPaper,Long> {
    Optional<ExamPaper> findById(@Nullable Long id);
}
