package com.example.jkedudemo.module.exam.repository;

import com.example.jkedudemo.module.exam.entity.ExamPaper;
import com.example.jkedudemo.module.exam.entity.MemberAnswerCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAnswerCategoryRepository extends JpaRepository<MemberAnswerCategory,Long> {
    List<MemberAnswerCategory> findByExamPaper(ExamPaper examPaper);


}
