package com.example.jkedudemo.module.member.dto.request;

import com.example.jkedudemo.module.common.enums.member.PhoneAuth;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindPasswordRequestDto {

    private String phone;

    private String smscode;
    private PhoneAuth phoneauth;
    public void setPhoneauth(String phoneauth) {
        this.phoneauth = PhoneAuth.valueOf(phoneauth.toUpperCase());
    }
    public PhoneAuth getPhoneauth() {
        return phoneauth;
    }

}
