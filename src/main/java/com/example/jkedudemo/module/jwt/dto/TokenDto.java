package com.example.jkedudemo.module.jwt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenDto {

    private String grantType;

    private String status;

    private String message;

    private String accessToken;

    private String refreshToken;



    //MEMBER_ID
    private String keyId;

    public TokenDto(String status, String message,String accessToken, String refreshToken) {
        this.status = status;
        this.message = message;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;

    }

}
