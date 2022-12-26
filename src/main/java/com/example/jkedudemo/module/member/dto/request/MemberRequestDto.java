package com.example.jkedudemo.module.member.dto.request;

import com.example.jkedudemo.module.common.enums.member.Role;
import com.example.jkedudemo.module.member.entity.Member;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {
    private Long id;
    private String email;
    private String password;
    private String phone;
    private String name;
    private Role role;
    private String academyId;
    public MemberRequestDto(String email, String password) {
        this.email=email;
        this.password=password;
    }
    //Roletype 바인딩
    public void setRole(String role) {
        this.role = Role.valueOf("ROLE_"+role.toUpperCase());
    }
    public Role getRole() {
        return role;
    }
    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .id(id)
                .phone(phone)
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .academyId(academyId)
                .role(getRole())
                .build();
    }
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}