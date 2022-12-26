package com.example.jkedudemo.module.exam.repository;

import com.example.jkedudemo.module.common.enums.Level;
import com.example.jkedudemo.module.exam.entity.ExamPaper;
import com.example.jkedudemo.module.exam.entity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ExamPaperRepository extends JpaRepository<ExamPaper,Long> {

    boolean existsByExamResult(ExamResult examResult);

    List<ExamPaper> findByExamResultAndLevelIn(ExamResult examResult, Collection<Level> level);

}
