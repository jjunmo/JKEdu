package com.example.jkedudemo.module.exam.repository;

import com.example.jkedudemo.module.exam.entity.ExamMultipleChoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ExamMultipleChoiceRepository extends JpaRepository<ExamMultipleChoice,Long> {
    List<ExamMultipleChoice> findByQuest_id(Long examQuest_id);
}
