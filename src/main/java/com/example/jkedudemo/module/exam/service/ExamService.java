package com.example.jkedudemo.module.exam.service;

import com.example.jkedudemo.module.exam.entity.ExamMultipleChoice;
import com.example.jkedudemo.module.exam.repository.ExamCategoryRepository;
import com.example.jkedudemo.module.exam.repository.ExamMultipleChoiceRepository;
import com.example.jkedudemo.module.exam.repository.ExamQuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExamService {

    private final ExamQuestRepository examQuestRepository;
    private final ExamMultipleChoiceRepository examMultipleChoiceRepository;
    private final ExamCategoryRepository examCategoryRepository;






}
