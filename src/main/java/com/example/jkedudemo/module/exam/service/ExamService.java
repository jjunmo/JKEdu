package com.example.jkedudemo.module.exam.service;

import com.example.jkedudemo.module.common.enums.exam.Exam;
import com.example.jkedudemo.module.common.enums.exam.Quest;
import com.example.jkedudemo.module.config.SecurityUtil;
import com.example.jkedudemo.module.exam.dto.ExamDTO;
import com.example.jkedudemo.module.exam.dto.ExamMultipleChoiceDTO;
import com.example.jkedudemo.module.exam.dto.ExamQuestDTO;
import com.example.jkedudemo.module.exam.entity.ExamCategory;
import com.example.jkedudemo.module.exam.entity.ExamQuest;
import com.example.jkedudemo.module.exam.repository.ExamCategoryRepository;
import com.example.jkedudemo.module.exam.repository.ExamMultipleChoiceRepository;
import com.example.jkedudemo.module.exam.repository.ExamQuestRepository;
import com.example.jkedudemo.module.handler.MyInternalServerException;
import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExamService {

    private final MemberRepository memberRepository;
    private final ExamQuestRepository examQuestRepository;
    private final ExamMultipleChoiceRepository examMultipleChoiceRepository;
    private final ExamCategoryRepository examCategoryRepository;

    Random rand = new Random();

    public Member isMemberCurrent() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new MyInternalServerException("로그인 유저 정보가 없습니다"));
    }
    public ExamDTO ExamQuest(String examCategory){
        //로그인 정보
        Member member = isMemberCurrent();
        //로그인된 유저의 레벨에 맞는 문제 List
        List<ExamQuest> examQuestList = examQuestRepository.findByExamCategory_ExamAndLevel(Exam.valueOf(examCategory), member.getLevel());
        //조회된 문제의 갯수
        int examQuestListSize = examQuestList.size();
        // 조회된 문제중 하나의 문제를 가져옴
        ExamQuest examQuestRandomElement = examQuestList.get(rand.nextInt(examQuestListSize));
        //DTO 담기
        ExamQuestDTO examQuestDTO=examQuestRandomElement.entityToDto();

        //객관식 문제의 경우 객관식 항목을 다 담기
        if(examQuestDTO.getQuest().equals(Quest.MULTIPLE)){
            List<ExamMultipleChoiceDTO> examMultipleChoiceDTOList=examQuestDTO.getMultipleChoice();
            examQuestRandomElement.entityToMultipleDto(examMultipleChoiceDTOList);
        }

        return ExamDTO.examDTO(examQuestDTO, Exam.valueOf(examCategory));

    }
}
