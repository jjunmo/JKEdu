package com.example.jkedudemo.module.exam.repository;

import com.example.jkedudemo.module.common.enums.Level;
import com.example.jkedudemo.module.common.enums.exam.Exam;
import com.example.jkedudemo.module.exam.entity.ExamCategory;
import com.example.jkedudemo.module.exam.entity.ExamQuest;
import com.example.jkedudemo.module.member.entity.MemberPhoneAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamQuestRepository extends JpaRepository<ExamQuest,Long> {

    List<ExamQuest> findByExamCategory_ExamAndLevel(Exam examCategory_exam, Level level);

    List<ExamQuest> findByExamCategory_ExamAndLevelAndIdNot(Exam examCategory_exam,Level level , Long id);

}
