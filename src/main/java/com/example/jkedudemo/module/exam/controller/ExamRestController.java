package com.example.jkedudemo.module.exam.controller;

import com.example.jkedudemo.module.common.enums.exam.Exam;
import com.example.jkedudemo.module.exam.dto.request.NextQuestRequest;
import com.example.jkedudemo.module.exam.dto.response.ExamFirstQuestResponse;
import com.example.jkedudemo.module.exam.dto.request.QuestRequest;
import com.example.jkedudemo.module.exam.dto.response.ExamNextQuestResponse;
import com.example.jkedudemo.module.exam.dto.response.ExamRefreshResponseDto;
import com.example.jkedudemo.module.exam.dto.response.TestResponseDto;
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

    @PostMapping
    public HttpEntity<ExamFirstQuestResponse> getQuest(@RequestParam("answer-paper") Long examPaperId){
        return ResponseEntity.ok(examService.ExamFirstQuest(examPaperId));
    }

    @PostMapping("/check")
    public HttpEntity<TestResponseDto> test(@RequestParam("category") String exam,@RequestParam(value="student",required = false) Long studentId){

        return ResponseEntity.ok(examService.test(exam,studentId));
    }


    @GetMapping("/next")
    public HttpEntity<ExamRefreshResponseDto> examRefresh(@RequestParam("answer-paper") Long examPaperId){
        return ResponseEntity.ok(examService.examRefresh(examPaperId));
    }

    @PostMapping("/next")
    public HttpEntity<ExamNextQuestResponse> getNextQuest(@RequestParam(value ="answer-paper") Long examPaperId, @RequestBody NextQuestRequest request){
        String nextEnd = examService.nextEnd(examPaperId);

        if(nextEnd.equals("NEXT")) return ResponseEntity.ok(examService.examNextQuestResponse(request,examPaperId));

        else return ResponseEntity.ok(ExamNextQuestResponse.examDTO2());
    }

}
