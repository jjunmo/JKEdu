package com.example.jkedudemo.module.member.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AcademyMemberResponseDto {
    private String status;
    private String condition;
    private Long studentId;
    private Long examId;

    public AcademyMemberResponseDto(String status, String condition) {
        this.status=status;
        this.condition=condition;
    }


}
