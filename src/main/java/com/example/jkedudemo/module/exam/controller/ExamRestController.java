package com.example.jkedudemo.module.exam.controller;

import com.example.jkedudemo.module.exam.dto.ExamDTO;
import com.example.jkedudemo.module.exam.entity.ExamCategory;
import com.example.jkedudemo.module.exam.service.ExamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exam")
@Slf4j
public class ExamRestController {
    private final ExamService examService;

    @GetMapping
    public HttpEntity<ExamDTO> getQuest(@RequestParam("exam")String exam){
        return ResponseEntity.ok(examService.ExamQuest(exam));
    }
}
