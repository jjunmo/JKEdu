package com.example.jkedudemo.module.member.dto.request;

import com.example.jkedudemo.module.common.enums.member.PhoneAuth;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindPasswordRequestDto {

    private String phone;

    private String smsCode;
    private PhoneAuth phoneAuth;
    public void setPhoneAuth(String phoneAuth) {
        this.phoneAuth = PhoneAuth.valueOf(phoneAuth.toUpperCase());
    }
    public PhoneAuth getPhoneAuth() {
        return phoneAuth;
    }

}
