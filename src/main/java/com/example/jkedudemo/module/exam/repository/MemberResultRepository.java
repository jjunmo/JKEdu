package com.example.jkedudemo.module.exam.repository;

import com.example.jkedudemo.module.exam.entity.ExamPaper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberResultRepository extends JpaRepository<ExamPaper,Long> {
}
