package com.example.jkeduhomepage.module.member.dto;

import com.example.jkeduhomepage.module.common.enums.Status;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
public class MemberRequestDTO {

    private String loginId = "";

    private String password = "";

    private String email = "";

    private String name = "";

    private String phone = "";

    private Status status=Status.RED;


    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(loginId, password);
    }
}
