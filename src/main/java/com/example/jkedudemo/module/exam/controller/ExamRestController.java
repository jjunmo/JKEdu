package com.example.jkedudemo.module.exam.controller;

import com.example.jkedudemo.module.exam.dto.request.NextQuestRequest;
import com.example.jkedudemo.module.exam.dto.response.ExamFirstQuestResponse;
import com.example.jkedudemo.module.exam.dto.request.QuestRequest;
import com.example.jkedudemo.module.exam.dto.response.ExamNextQuestResponse;
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
    public HttpEntity<ExamFirstQuestResponse> getQuest(@RequestBody QuestRequest request){
        return ResponseEntity.ok(examService.ExamFirstQuest(request));
    }

    @PostMapping("/next")
    public HttpEntity<ExamNextQuestResponse> getNextQuest(@RequestBody NextQuestRequest request){
        String nextEnd = examService.nextEnd(request.getExamId(), request.getNumber());

        if(nextEnd.equals("NEXT")) return ResponseEntity.ok(examService.examNextQuestResponse(request));

        else return ResponseEntity.ok(ExamNextQuestResponse.examDTO2());
    }
}
