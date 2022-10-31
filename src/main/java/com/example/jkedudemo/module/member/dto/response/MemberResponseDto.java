package com.example.jkedudemo.module.member.dto.response;


import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.common.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class MemberResponseDto {
    private String email;
    private String phoneNumber;
    private RoleType roleType;

    public static MemberResponseDto of(Member member) {
            return MemberResponseDto.builder()
                    .phoneNumber(member.getPhoneNumber())
                    .roleType(member.getRoleType())
                    .email(member.getEmail())
                    .build();

    }
}