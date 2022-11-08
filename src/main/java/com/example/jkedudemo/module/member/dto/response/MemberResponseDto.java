package com.example.jkedudemo.module.member.dto.response;


import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.common.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class MemberResponseDto {
    private String email;
    private String name;
    private String phone;
    private Role role;
    private String academyId;
    private Date birth;
    private HttpStatusResopnse httpStatusResopnse;

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .httpStatusResopnse(new HttpStatusResopnse())
                .name(member.getName())
                .birth(member.getBirth())
                .phone(member.getPhone())
                .role(member.getRole())
                .email(member.getEmail())
                .academyId(member.getAcademyId())
                .build();
    }

}