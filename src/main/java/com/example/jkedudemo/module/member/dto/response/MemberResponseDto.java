package com.example.jkedudemo.module.member.dto.response;


import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.common.enums.RoleType;
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
    private String phoneNumber;
    private RoleType roleType;
    private String academyId;
    private Date birth;

    public static MemberResponseDto of(Member member) {
            return MemberResponseDto.builder()
                    .name(member.getName())
                    .birth(member.getBirth())
                    .phoneNumber(member.getPhoneNumber())
                    .roleType(member.getRoleType())
                    .email(member.getEmail())
                    .academyId(member.getAcademyId())
                    .build();

    }
}