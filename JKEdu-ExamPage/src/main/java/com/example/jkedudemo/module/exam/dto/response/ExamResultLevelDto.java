package com.example.jkedudemo.module.exam.dto.response;

import com.example.jkedudemo.module.common.enums.Level;
import com.example.jkedudemo.module.common.enums.exam.Exam;
import lombok.*;

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
