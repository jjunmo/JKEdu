package com.example.jkeduhomepage.module.member.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberRequestDTO {

    private String loginId = "";

    private String password = "";

    private String email;

    private String name;

    private String phone;

    private String newPassword;


    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(loginId, password);
    }
}
