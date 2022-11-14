package com.example.jkedudemo.module.exam.controller;

import com.example.jkedudemo.module.exam.service.ExamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exam")
@Slf4j
public class ExamController {
    private final ExamService examService;
}
