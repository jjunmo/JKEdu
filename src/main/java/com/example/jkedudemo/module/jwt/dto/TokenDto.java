package com.example.jkedudemo.module.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {

    private String grantType;

    private String accessToken;

    private String refreshToken;

    private String status;

    private String message;

    //MEMBER_ID
    private String keyId;

    public TokenDto(String accessToken, String refreshToken, String status, String message) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.status = status;
        this.message = message;
    }
}
