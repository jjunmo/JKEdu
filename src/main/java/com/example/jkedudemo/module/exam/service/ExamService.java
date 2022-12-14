package com.example.jkedudemo.module.exam.service;

import com.example.jkedudemo.module.common.enums.Level;
import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.common.enums.exam.Exam;
import com.example.jkedudemo.module.common.enums.exam.Quest;
import com.example.jkedudemo.module.common.enums.member.Role;
import com.example.jkedudemo.module.config.SecurityUtil;
import com.example.jkedudemo.module.exam.dto.request.NextQuestRequest;
import com.example.jkedudemo.module.exam.dto.response.ExamFirstQuestResponse;
import com.example.jkedudemo.module.exam.dto.response.ExamNextQuestResponse;
import com.example.jkedudemo.module.exam.dto.response.ExamRefreshResponseDto;
import com.example.jkedudemo.module.exam.dto.response.TestResponseDto;
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
import java.util.stream.Collectors;

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
    private final ExamPaperRepository examPaperRepository;

    Random rand = new Random();

    //로그인된 회원 확인
    public Member isMemberCurrent() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new MyInternalServerException("로그인 유저 정보가 없습니다"));
    }

    //시험중인 답안지 확인
    public ExamPaper isExamPaper(Long id) {
        return examPaperRepository.findById(id)
                .orElseThrow(() -> new MyInternalServerException("시험을 다시 시작하세요"));
    }


    //TODO:시험중 새로고침 시험치던 문제화면

    @Transactional
    public ExamFirstQuestResponse ExamFirstQuest(Long examPaperId){
        ExamPaper examPaper = isExamPaper(examPaperId);

        //DTO 담기
        List<MemberAnswer> memberAnswerList=memberAnswerRepository.findByMemberAnswerCategory_ExamPaper(examPaper);
        List<MemberAnswerCategory> memberAnswerCategoryList=memberAnswerCategoryRepository.findByExamPaper(examPaper);

        if(memberAnswerList.isEmpty()) log.info("첫 시험입니당");

        boolean memberAnswerGet = memberAnswerList.stream().anyMatch(m->m.getMyAnswer().equals(""));

        if (!memberAnswerGet) {
            List<ExamQuest> examQuestList = examQuestRepository.findByExamCategory_ExamAndLevel(examPaper.getExamCategory(),Level.PRE_A1);
            //조회된 문제의 갯수
            int examQuestListSize = examQuestList.size();
            // 조회된 문제중 하나의 문제를 가져옴
            ExamQuest examQuestRandomElement = examQuestList.get(rand.nextInt(examQuestListSize));

            memberAnswerCategoryList.get(0).setExamCategory(examQuestRandomElement.getExamCategory());

            memberAnswerRepository.save(new MemberAnswer(null,memberAnswerCategoryList.get(0),examQuestRandomElement,"",null));
            //객관식 문제의 경우 객관식 항목을 다 담기
            if(examQuestRandomElement.getQuest().equals(Quest.MULTIPLE)){
                List<ExamMultipleChoice> examMultipleChoice=examMultipleChoiceRepository.findByQuest_id(examQuestRandomElement.getId());
                return ExamFirstQuestResponse.examDTO(examQuestRandomElement.entityToMultipleDto(examMultipleChoice),1) ;
            }
            return ExamFirstQuestResponse.examDTO(examQuestRandomElement.entityToDto(),1);

        } else {

            MemberAnswer memberAnswer = memberAnswerList.stream()
                    .filter(m -> m.getMyAnswer().equals(""))
                    .collect(Collectors.toList())
                    .get(0);

            if(memberAnswer.getExamQuest().getQuest().equals(Quest.MULTIPLE)){
                List<ExamMultipleChoice> examMultipleChoice=examMultipleChoiceRepository.findByQuest_id(memberAnswer.getExamQuest().getId());
                return ExamFirstQuestResponse.examDTO(memberAnswer.getExamQuest().entityToMultipleDto(examMultipleChoice),1) ;
            }

            return ExamFirstQuestResponse.examDTO(memberAnswer.getExamQuest().entityToDto(), memberAnswerList.size());

        }

    }

    @Transactional
    public TestResponseDto test(String exam,Long studentId){
        Member member=isMemberCurrent();

        if(member.getRole().equals(Role.ROLE_ACADEMY)) {
            Optional<Member> memberOptional = memberRepository.findById(studentId);
            member = memberOptional.orElseGet(this::isMemberCurrent);

            Optional<MemberAnswer> memberAnswerOptional=memberAnswerRepository.findByMyAnswerAndExamQuest_ExamCategory_ExamAndMemberAnswerCategory_Member("",Exam.valueOf(exam),member);
            if(memberAnswerOptional.isPresent()) {
                return TestResponseDto.statusOk(memberAnswerOptional.get().getMemberAnswerCategory().getExamPaper());
            }
        }

        Member memberAcademy = memberRepository.findByRoleAndAcademyId(Role.ROLE_ACADEMY,member.getAcademyId());

        if(member.getRole().equals(Role.ROLE_ACADEMY_STUDENT)){
            if (memberAcademy.getTestCount() <= 0) throw new MyInternalServerException("해당 학원에 테스트 횟수가 없습니다.");
            else {
                memberAcademy.setTestCount(memberAcademy.getTestCount() - 1);
                memberRepository.save(memberAcademy);
            }

        }else {
            if (member.getTestCount() <= 0) throw new MyInternalServerException("테스트 횟수가 없습니다.");
            else {
                member.setTestCount(member.getTestCount() - 1);
                memberRepository.save(member);
            }
        }

        ExamPaper examPaper=examPaperRepository.save(new ExamPaper(null,null, Exam.valueOf(exam)));
        memberAnswerCategoryRepository.save(new MemberAnswerCategory(null,member,null,examPaper));

        return TestResponseDto.statusOk(examPaper);
    }

    @Transactional
    public ExamNextQuestResponse examNextQuestResponse(NextQuestRequest request,Long examPaperId){
        //일반유저인지 학원유저인지 확인
        Member member=isMemberCurrent();
        ExamPaper examPaper = isExamPaper(examPaperId);

        List<MemberAnswerCategory> memberAnswerCategoryList=memberAnswerCategoryRepository.findByExamPaper(examPaper);
        if(memberAnswerCategoryList.isEmpty()) throw new MyInternalServerException("올바른 접근이 아닙니다.no examPaper");

        if(member.getRole().equals(Role.ROLE_ACADEMY)) {
            Optional<Member> memberOptional = memberRepository.findById(memberAnswerCategoryList.get(0).getMember().getId());
            member = memberOptional.orElseGet(this::isMemberCurrent);
        }

        //등급을 배열로
        Level[] levels=Level.values();

        //시험에 나온문제 확인
        Optional<ExamQuest> examQuestOptional = examQuestRepository.findById(request.getExamId());

        MemberAnswer memberAnswer=memberAnswerRepository.findByExamQuest_Id(request.getExamId());
        memberAnswer.setMyAnswer(request.getMyAnswer());

        if(examQuestOptional.isEmpty()) throw new MyInternalServerException("잘못된 접근입니다.");

        ExamQuest examQuest = examQuestOptional.get();

        Level level = examQuest.getLevel();
        int levelCheck = level.ordinal();
        Level changeLevel;

            if(examQuest.getRightAnswer().equals(memberAnswer.getMyAnswer())){

                if(levelCheck < 5) changeLevel=levels[levelCheck + 1];
                else changeLevel=levels[levelCheck];

                memberAnswer.setAnswerYN(YN.Y);
                memberAnswerRepository.save(memberAnswer);

            }else{

                if(levelCheck == 0) changeLevel=(levels[levelCheck]);
                else changeLevel=(levels[levelCheck-1]);

                memberAnswer.setAnswerYN(YN.N);
                memberAnswerRepository.save(memberAnswer);
            }

            if(examQuest.getExamCategory().getExam().getValue() == memberAnswerCategoryList.size()){
                //맞춘 문제 확인해서 시험 등급측정
                List<MemberAnswer> memberAnswerList = memberAnswerRepository.findByMemberAnswerCategory_ExamPaperAndAnswerYN(examPaper, YN.Y);
                int sum = memberAnswerList.stream().mapToInt(m -> m.getExamQuest().getLevel().getValue()).sum();
                examPaper.setLevel(Level.PRE_A1.getLevel(sum));
                return ExamNextQuestResponse.examDTO2();
            }

            List<ExamQuest> examQuestList = examQuestRepository.findByExamCategory_ExamAndLevel(examQuest.getExamCategory().getExam(),changeLevel);

            List<ExamQuest> resultList1 = examQuestList.stream()
                    .filter(o -> memberAnswerCategoryList
                    .stream()
                            .noneMatch(n -> o.getExamCategory().getId().equals(n.getExamCategory().getId())))
                    .collect(Collectors.toList());

            int examQuestListSize = resultList1.size();

            // 조회된 문제중 하나의 문제를 가져옴
            ExamQuest examQuestRandomElement = resultList1.get(rand.nextInt(examQuestListSize));

            //DTO 담기
            MemberAnswerCategory nextMemberAnswerCategory= memberAnswerCategoryRepository.save(new MemberAnswerCategory(null,member,examQuestRandomElement.getExamCategory(),examPaper));
            memberAnswerRepository.save(new MemberAnswer(null,nextMemberAnswerCategory,examQuestRandomElement,"", YN.N));

            //객관식 문제의 경우 객관식 항목을 다 담기
            if(examQuestRandomElement.getQuest().equals(Quest.MULTIPLE)){
                List<ExamMultipleChoice> examMultipleChoice=examMultipleChoiceRepository.findByQuest_id(examQuestRandomElement.getId());
                return ExamNextQuestResponse.examDTO(examQuestRandomElement.entityToMultipleDto(examMultipleChoice),memberAnswerCategoryList.size());
            }

            return ExamNextQuestResponse.examDTO(examQuestRandomElement.entityToDto(), memberAnswerCategoryList.size());

        }

    public String nextEnd(Long examPaperId) {
        int number;

        ExamPaper examPaper=isExamPaper(examPaperId);

        List<MemberAnswerCategory> memberAnswerCategoryList= memberAnswerCategoryRepository.findByExamPaper(examPaper);

        if(memberAnswerCategoryList.isEmpty()) return "NEXT";

        Exam exam = examPaper.getExamCategory();

        number = memberAnswerCategoryList.size();

        if (number <= exam.getValue())
            return "NEXT";

        else return "END";

    }

    public ExamRefreshResponseDto examRefresh(Long examPaperId) {
        ExamPaper examPaper=isExamPaper(examPaperId);

        List<MemberAnswer> memberAnswerList=memberAnswerRepository.findByMemberAnswerCategory_ExamPaper(examPaper);

        log.info("풀지않은 문제를 확인");

        MemberAnswer memberAnswer = memberAnswerList.stream()
                .filter(m -> m.getMyAnswer().equals(""))
                .collect(Collectors.toList())
                .get(0);

        return ExamRefreshResponseDto.examDTO(memberAnswer.getExamQuest().entityToDto(), memberAnswerList.size());

    }
}

