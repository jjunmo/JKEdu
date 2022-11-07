package com.example.jkedudemo.module.member.dto.request;

import com.example.jkedudemo.module.common.enums.Phoneauth;
import com.example.jkedudemo.module.common.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindPasswordRequestDto {

    private String phone;

    private String smscode;
    private Phoneauth phoneauth;

    public void setPhoneauth(String phoneauth) {
        this.phoneauth = Phoneauth.valueOf(phoneauth.toUpperCase());
    }

    public Phoneauth getPhoneauth() {
        return phoneauth;
    }

    public UsernamePasswordAuthenticationToken toAuthentication(String email ,String password) {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

}
