package com.example.jkedudemo.module.exam.repository;

import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.common.enums.exam.Exam;
import com.example.jkedudemo.module.exam.entity.ExamPaper;
import com.example.jkedudemo.module.exam.entity.MemberAnswer;
import com.example.jkedudemo.module.exam.entity.MemberAnswerCategory;
import com.example.jkedudemo.module.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberAnswerRepository extends JpaRepository<MemberAnswer,Long> {
    List<MemberAnswer> findByMemberAnswerCategory_ExamPaperAndAnswerYN(ExamPaper memberAnswerCategory_ExamPaper,YN answerYN);

    Optional<MemberAnswer> findByMyAnswerAndExamQuest_ExamCategory_ExamAndMemberAnswerCategory_Member(String myAnswer, Exam exam, Member member);

    List<MemberAnswer> findByMemberAnswerCategory_ExamPaper(ExamPaper examPaper);

    MemberAnswer findByExamQuest_IdAndMemberAnswerCategory_ExamPaper(Long id,ExamPaper examPaper);
}
