package com.example.jkedudemo.module.member.dto.request;

import com.example.jkedudemo.module.common.enums.member.Phoneauth;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

}
