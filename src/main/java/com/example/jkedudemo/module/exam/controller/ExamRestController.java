package com.example.jkedudemo.module.exam.controller;

import com.example.jkedudemo.module.exam.dto.request.NextQuestRequest;
import com.example.jkedudemo.module.exam.dto.response.*;
import com.example.jkedudemo.module.exam.service.ExamService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exam")
@Slf4j
public class ExamRestController {

    private final ExamService examService;

    /**
     * 시험 시작 첫 문제
     * @param examPaperId PK
     * @return 시험중인 문제 , 랜덤으로 첫문제
     */
    @PostMapping
    @Operation(summary = "첫 문제", description = "첫 번째 문제 가져오기")
    public HttpEntity<ExamFirstQuestResponse> getQuest(@RequestParam("answer-paper-id") Long examPaperId){
        return ResponseEntity.ok(examService.ExamFirstQuest(examPaperId));
    }

    /**
     * 테스트 횟수 체크 , 이전 시험
     * @param exam 시험 응시 영역
     * @param studentId ROLE_ACADEMY 경우 응시 전 넘어옴
     * @return  examPaper,memberAnswerCategory save
     */
    @PostMapping("/check")
    @Operation(summary = "시험 영역 선택 후 test 횟수 체크", description = "test 횟수 체크 및 answer-paper 생성")
    public HttpEntity<TestResponseDto> test(@RequestParam(value = "exam-id") Long examId,@RequestParam("category") String exam,@RequestParam(value="student-id",required = false) Long studentId){

        return ResponseEntity.ok(examService.test(examId,exam,studentId));
    }


    @GetMapping("/next")
    @Operation(summary = "시험 중 새로고침", description = "시험 중 새로고침시 기존 풀던문제 확인하여 응답")
    public HttpEntity<ExamRefreshResponseDto> examRefresh(@RequestParam("answer-paper-id") Long examPaperId){
        return ResponseEntity.ok(examService.examRefresh(examPaperId));
    }

    @PostMapping("/next")
    @Operation(summary = "다음 문제", description = "영역에 따른 시험문제 갯수 파악하여 answer-paper 에 정답 저장")
    public HttpEntity<ExamNextQuestResponse> getNextQuest(@RequestParam(value = "answer-paper-id") Long examPaperId, @RequestBody NextQuestRequest request){
        String nextEnd = examService.nextEnd(examPaperId);

        if(nextEnd.equals("NEXT")) return ResponseEntity.ok(examService.examNextQuestResponse(request,examPaperId));

        else return ResponseEntity.ok(ExamNextQuestResponse.examDTO2());
    }

    @PostMapping("/run")
    @Operation(summary = "시험 포기", description = "시험 중단하고 등급 확인")
    public HttpEntity<ExamNextQuestResponse> setQuest(@RequestParam(value = "answer-paper-id") Long examPaperId){
        return ResponseEntity.ok(examService.setQuest(examPaperId));
    }

    @PostMapping("/new")
    @Operation(summary = "시험 응시", description = "시험 응시 examId 응답")
    public HttpEntity<ExamStartResponseDto> examStart(){
        return ResponseEntity.ok(examService.examStart());

    }

    @GetMapping("/check")
    @Operation(summary = "시험중 응시영역 확인", description = "응시된 영역")
    public HttpEntity<ExamResultCheckResponseDto> resultCheck(@RequestParam(value = "exam-id") Long examId){

        return ResponseEntity.ok(examService.resultCheck(examId));
    }

    @GetMapping("/result")
    @Operation(summary = "시험결과", description = "시험결과")
    public HttpEntity<ExamResultResponseDto> getResult(@RequestParam(value = "exam-id") Long examId){
        return ResponseEntity.ok(examService.result(examId));
    }

}
