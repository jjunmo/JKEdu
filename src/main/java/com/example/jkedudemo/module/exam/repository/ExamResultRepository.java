package com.example.jkedudemo.module.exam.repository;

import com.example.jkedudemo.module.common.enums.exam.Exam;
import com.example.jkedudemo.module.exam.entity.ExamResult;
import com.example.jkedudemo.module.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamResultRepository extends JpaRepository<ExamResult,Long> {

    List<ExamResult> findByMember(Member member);

}
