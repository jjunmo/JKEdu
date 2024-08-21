package com.example.jkedudemo.module.member.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
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
