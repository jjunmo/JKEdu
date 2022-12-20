package com.example.jkedudemo.module.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberStatusOkResponseDto {
    private String status;
    private String message;

    public static MemberStatusOkResponseDto statusOk(){
        return MemberStatusOkResponseDto.builder()
                .status("200")
                .message("OK")
                .build();
    }


}
