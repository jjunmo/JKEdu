package com.example.jkedudemo.module.exam.controller;

import com.example.jkedudemo.module.exam.dto.request.NextQuestRequest;
import com.example.jkedudemo.module.exam.dto.response.*;
import com.example.jkedudemo.module.exam.service.ExamService;
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
    public HttpEntity<TestResponseDto> test(@RequestParam(value = "exam-id") Long examId,@RequestParam("category") String exam,@RequestParam(value="student-id",required = false) Long studentId){

        return ResponseEntity.ok(examService.test(examId,exam,studentId));
    }


    @GetMapping("/next")
    public HttpEntity<ExamRefreshResponseDto> examRefresh(@RequestParam("answer-paper-id") Long examPaperId){
        return ResponseEntity.ok(examService.examRefresh(examPaperId));
    }

    @PostMapping("/next")
    public HttpEntity<ExamNextQuestResponse> getNextQuest(@RequestParam(value = "answer-paper-id") Long examPaperId, @RequestBody NextQuestRequest request){
        String nextEnd = examService.nextEnd(examPaperId);

        if(nextEnd.equals("NEXT")) return ResponseEntity.ok(examService.examNextQuestResponse(request,examPaperId));

        else return ResponseEntity.ok(ExamNextQuestResponse.examDTO2());
    }

    @PostMapping("/run")
    public HttpEntity<ExamNextQuestResponse> setQuest(@RequestParam(value = "answer-paper-id") Long examPaperId){
        return ResponseEntity.ok(examService.setQuest(examPaperId));
    }

    @PostMapping("/new")
    public HttpEntity<ExamStartResponseDto> examStart(){
        return ResponseEntity.ok(examService.examStart());
    }

}
