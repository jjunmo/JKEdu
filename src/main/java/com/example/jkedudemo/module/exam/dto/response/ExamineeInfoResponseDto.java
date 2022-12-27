package com.example.jkedudemo.module.exam.dto.response;

import com.example.jkedudemo.module.exam.entity.ExamResult;
import com.example.jkedudemo.module.member.entity.Member;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamineeInfoResponseDto {
    String name;
    String phone;
    LocalDate examDate;

    public static ExamineeInfoResponseDto resultLevelDto(Member member, ExamResult examResult){
        return ExamineeInfoResponseDto.builder()
                .name(member.getName())
                .phone(member.getPhone())
                .examDate(examResult.getCreateDate())
                .build();
    }
}
