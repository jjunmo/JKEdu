package com.example.jkedudemo.module.member.dto.request;

import com.example.jkedudemo.module.common.enums.member.Role;
import com.example.jkedudemo.module.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberRequestDto {
    private String email;
    private String password;
    private String phone;
    private String name;
    private String role;
    public MemberRequestDto(String email, String password) {
        this.email=email;
        this.password=password;
    }
    //Roletype 바인딩
    public void setRole(String role) {
        this.role = "ROLE_"+role.toUpperCase();
    }

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .phone(phone)
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.valueOf(role))
                .build();
    }
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}