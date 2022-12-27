package com.example.jkedudemo.module.exam.dto.response;

import com.example.jkedudemo.module.common.enums.Level;
import com.example.jkedudemo.module.common.enums.exam.Exam;
import com.example.jkedudemo.module.exam.entity.ExamPaper;
import lombok.*;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamResultLevelDto {

    Exam category;
    Integer correctCount;
    Integer problemCount;
    Level level;

}
