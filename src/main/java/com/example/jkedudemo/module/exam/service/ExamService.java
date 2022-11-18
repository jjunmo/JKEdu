package com.example.jkedudemo.module.exam.service;

import com.example.jkedudemo.module.common.util.MemberCurrent;
import com.example.jkedudemo.module.exam.dto.ExamDTO;
import com.example.jkedudemo.module.exam.entity.ExamMultipleChoice;
import com.example.jkedudemo.module.exam.repository.ExamCategoryRepository;
import com.example.jkedudemo.module.exam.repository.ExamMultipleChoiceRepository;
import com.example.jkedudemo.module.exam.repository.ExamQuestRepository;
import com.example.jkedudemo.module.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.example.jkedudemo.module.common.util.MemberCurrent.*;
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExamService {

    private final ExamQuestRepository examQuestRepository;
    private final ExamMultipleChoiceRepository examMultipleChoiceRepository;
    private final ExamCategoryRepository examCategoryRepository;

    public String Exam(ExamDTO examDTO){
        //로그인 정보
        Member member = isMemberCurrent();
        //TODO : ExamQuestRepository 작성 필요 ,
        return "ok";
    }

}
