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
    private String password;
    private String phone;
    private RoleType roleType;
    private String academyId;

    //RoleType 바인딩 처리
    public void setRoleType(String roleType) {
        this.roleType = RoleType.valueOf("ROLE_"+roleType.toUpperCase());
    }

    public RoleType getRoleType() {
        return roleType;
    }



    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .phone(phone)
                .email(email)
                .password(passwordEncoder.encode(password))
                .academyId(academyId)
                .roleType(getRoleType())
                .build();
    }


    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}