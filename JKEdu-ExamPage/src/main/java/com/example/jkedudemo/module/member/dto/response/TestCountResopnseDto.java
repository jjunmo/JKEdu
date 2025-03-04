package com.example.jkedudemo.module.member.dto.response;

import com.example.jkedudemo.module.member.entity.Member;
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
public class TestCountResopnseDto {
    private String status;
    private String message;
    private String name;
    private int testCount;

    public static TestCountResopnseDto testCount(Member member){
        return TestCountResopnseDto.builder()
                .status("200")
                .message("OK")
                .name(member.getName())
                .testCount(member.getTestCount())
                .build();
    }
}
