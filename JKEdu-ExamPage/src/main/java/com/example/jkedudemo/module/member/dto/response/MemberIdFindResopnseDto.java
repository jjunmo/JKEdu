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
public class MemberIdFindResopnseDto {
    private String status;
    private String message;
    private String email;

    public static MemberIdFindResopnseDto idFind(Member member){
        return MemberIdFindResopnseDto.builder()
                .status("200")
                .message("OK")
                .email(member.getEmail())
                .build();
    }
}
