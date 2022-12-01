package com.example.jkedudemo.module.exam.service;

import com.example.jkedudemo.module.common.enums.Level;
import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.common.enums.exam.Quest;
import com.example.jkedudemo.module.common.enums.member.Role;
import com.example.jkedudemo.module.config.SecurityUtil;
import com.example.jkedudemo.module.exam.dto.request.NextQuestRequest;
import com.example.jkedudemo.module.exam.dto.response.ExamFirstQuestResponse;
import com.example.jkedudemo.module.exam.dto.request.QuestRequest;
import com.example.jkedudemo.module.exam.dto.response.ExamNextQuestResponse;
import com.example.jkedudemo.module.exam.entity.*;
import com.example.jkedudemo.module.exam.repository.*;
import com.example.jkedudemo.module.handler.MyInternalServerException;
import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExamService {

    private final MemberRepository memberRepository;
    private final ExamQuestRepository examQuestRepository;
    private final ExamMultipleChoiceRepository examMultipleChoiceRepository;
    private final MemberAnswerRepository memberAnswerRepository;
    private final MemberAnswerCategoryRepository memberAnswerCategoryRepository;
    private final MemberResultRepository memberResultRepository;

    Random rand = new Random();

    public Member isMemberCurrent() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new MyInternalServerException("로그인 유저 정보가 없습니다"));
    }

    @Transactional
    public ExamFirstQuestResponse ExamFirstQuest(QuestRequest request){
        //로그인 정보
        Member member = isMemberCurrent();



        if(member.getTestCount()<=0){
            throw new MyInternalServerException("테스트 횟수가 없습니다.");
        }else{
            member.setTestCount(member.getTestCount()-1);
            memberRepository.save(member);
        }

        ExamPaper examPaper =memberResultRepository.save(new ExamPaper());

        //로그인된 유저의 레벨에 맞는 문제 List
        List<ExamQuest> examQuestList = examQuestRepository.findByExamCategory_ExamAndLevel(request.getExam(),Level.PRE_A1);
        //조회된 문제의 갯수
        int examQuestListSize = examQuestList.size();
        // 조회된 문제중 하나의 문제를 가져옴
        ExamQuest examQuestRandomElement = examQuestList.get(rand.nextInt(examQuestListSize));
        //DTO 담기

        //객관식 문제의 경우 객관식 항목을 다 담기
        if(examQuestRandomElement.getQuest().equals(Quest.MULTIPLE)){
            List<ExamMultipleChoice> examMultipleChoice=examMultipleChoiceRepository.findByQuest_id(examQuestRandomElement.getId());
            return ExamFirstQuestResponse.examDTO(examQuestRandomElement.entityToMultipleDto(examMultipleChoice),examPaper);
        }
        return ExamFirstQuestResponse.examDTO(examQuestRandomElement.entityToDto(),examPaper);
    }

    @Transactional
    public ExamNextQuestResponse examNextQuestResponse(NextQuestRequest request){
        //일반유저인지 학원유저인지 확인
        Member member=isMemberCurrent();
        if(member.getRole().equals(Role.ROLE_ACADEMY)) {
            Optional<Member> memberOptional = memberRepository.findById(request.getStudentId());
            member = memberOptional.orElseGet(this::isMemberCurrent);
        }
        //등급을 배열로
        Level[] levels=Level.values();

        Optional<ExamPaper> examPaperOptional=memberResultRepository.findById(request.getExamPaper());
        if(examPaperOptional.isEmpty()){
            throw new MyInternalServerException("잘못된 접근입니다.");
        }
        ExamPaper examPaper = examPaperOptional.get();

        //시험에 나온문제 확인
        Optional<ExamQuest> examQuestOptional = examQuestRepository.findById(request.getExamId());

        if(examQuestOptional.isPresent()){
            ExamQuest examQuest = examQuestOptional.get();

            Level level = examQuest.getLevel();
            int levelCheck = level.ordinal();
            Level changeLevel;

            MemberAnswerCategory memberAnswerCategory=new MemberAnswerCategory(null,member,examQuest.getExamCategory(),examPaper);
            memberAnswerCategoryRepository.save(memberAnswerCategory);

            if(examQuest.getRightAnswer().equals(request.getMyAnswer())){

                if(levelCheck < 5){
                    changeLevel= levels[levelCheck + 1];
                }else{
                    changeLevel=levels[levelCheck];
                }

                MemberAnswer memberAnswer=new MemberAnswer(null,memberAnswerCategory,examQuest, request.getMyAnswer(), YN.Y);
                memberAnswerRepository.save(memberAnswer);
            }else{
                if(levelCheck == 0){
                    changeLevel=(levels[levelCheck]);
                }else{
                    changeLevel=(levels[levelCheck-1]);
                }

                MemberAnswer memberAnswer=new MemberAnswer(null,memberAnswerCategory,examQuest, request.getMyAnswer(), YN.N);
                memberAnswerRepository.save(memberAnswer);
            }

            List<ExamQuest> examQuestList = examQuestRepository.findByExamCategory_ExamAndLevel(examQuest.getExamCategory().getExam(),changeLevel);


            int examQuestListSize = examQuestList.size();

            // 조회된 문제중 하나의 문제를 가져옴
            ExamQuest examQuestRandomElement = examQuestList.get(rand.nextInt(examQuestListSize));
            //DTO 담기

            //객관식 문제의 경우 객관식 항목을 다 담기
            if(examQuestRandomElement.getQuest().equals(Quest.MULTIPLE)){
                List<ExamMultipleChoice> examMultipleChoice=examMultipleChoiceRepository.findByQuest_id(examQuestRandomElement.getId());
                return ExamNextQuestResponse.examDTO(examQuestRandomElement.entityToMultipleDto(examMultipleChoice),examPaper,request.getNumber(), request.getStudentId());
            }

            return ExamNextQuestResponse.examDTO(examQuestRandomElement.entityToDto(),examPaper, request.getNumber(), request.getStudentId());

        }

        // 이전 문제가 조회되지 않음.
        throw new MyInternalServerException("잘못된 접근입니다.");

    }

}