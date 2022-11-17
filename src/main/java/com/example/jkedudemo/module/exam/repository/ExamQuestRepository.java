package com.example.jkedudemo.module.exam.repository;

import com.example.jkedudemo.module.exam.entity.ExamQuest;
import com.example.jkedudemo.module.member.entity.MemberPhoneAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamQuestRepository extends JpaRepository<ExamQuest,Long> {
}
