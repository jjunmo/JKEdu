package com.example.jkedudemo.module.exam.repository;

import com.example.jkedudemo.module.exam.entity.ExamMultipleChoice;
import com.example.jkedudemo.module.exam.entity.ExamQuest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamMultipleChoiceRepository extends JpaRepository<ExamMultipleChoice,Long> {
}
