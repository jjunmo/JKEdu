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
public class MemberMyInfoResponseDto {
    private String status;
    private String message;
    private String email;
    private String name;
    private String phone;
    private String academyId;
    private Integer testCount;

    public static MemberMyInfoResponseDto myInfo(Member member) {
        return MemberMyInfoResponseDto.builder()
                .status("200")
                .message("OK")
                .name(member.getName())
                .phone(member.getPhone())
                .email(member.getEmail())
                .academyId(member.getAcademyId())
                .testCount(member.getTestCount())
                .build();
    }
}