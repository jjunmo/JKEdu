package com.example.jkedudemo.module.member.dto.request;

import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.common.enums.RoleType;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {
    private String email;
    private String memberPassword;
    private String phoneNumber;
    private RoleType roleType;
    private String academyId;



    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .phoneNumber(phoneNumber)
                .email(email)
                .memberPassword(passwordEncoder.encode(memberPassword))
                .academyId(academyId)
                .roleType(roleType)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, memberPassword);
    }
}