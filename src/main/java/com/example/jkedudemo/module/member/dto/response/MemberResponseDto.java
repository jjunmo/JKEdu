package com.example.jkedudemo.module.member.dto.response;


import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.role.RoleType;
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
    private String phone_number;
    private RoleType roleType;

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .phone_number(member.getPhone_number())
                .roleType(member.getRole_type())
                .email(member.getEmail())
                .build();

    }
}