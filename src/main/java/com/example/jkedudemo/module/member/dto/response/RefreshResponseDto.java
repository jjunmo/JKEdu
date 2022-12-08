package com.example.jkedudemo.module.member.dto.response;

import com.example.jkedudemo.module.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshResponseDto {
    private String status;
    private String message;

    public static RefreshResponseDto myInfo(Member member) {
        return RefreshResponseDto.builder()
                .status("200")
                .message("OK")
                .build();
    }


}
