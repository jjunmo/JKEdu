package com.example.jkedudemo.module.member.dto.response;

import com.example.jkedudemo.module.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcademyMemberResponseDto {
    private String status;
    private String message;
    private Long id;

    public static AcademyMemberResponseDto academyExamId(Member member){
        return AcademyMemberResponseDto.builder()
                .status("200")
                .message("OK")
                .id(member.getId())
                .build();
    }
}
