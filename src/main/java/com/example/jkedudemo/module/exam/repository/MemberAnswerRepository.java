package com.example.jkedudemo.module.exam.repository;

import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.exam.entity.ExamPaper;
import com.example.jkedudemo.module.exam.entity.MemberAnswer;
import com.example.jkedudemo.module.exam.entity.MemberAnswerCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface MemberAnswerRepository extends JpaRepository<MemberAnswer,Long> {
    List<MemberAnswer> findByMemberAnswerCategory_ExamPaperAndAnswerYN(ExamPaper memberAnswerCategory_ExamPaper,YN answerYN);

    MemberAnswer findByAnswerYNAndMemberAnswerCategory_ExamPaper(YN answerYN,ExamPaper memberAnswerCategory_ExamPaper);

    List<MemberAnswer> findByMemberAnswerCategory_ExamPaper(ExamPaper examPaper);
}
