package com.example.jkedudemo.module.exam.dto.response;

import com.example.jkedudemo.module.member.dto.response.MemberStatusOkResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestResponseDto {
    private String status;
    private String message;

    public static TestResponseDto statusOk(){
        return TestResponseDto.builder()
                .status("200")
                .message("OK")
                .build();
    }
}
