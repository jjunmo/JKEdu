package com.example.jkedudemo.module.member.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AcademyManagementResponseDto {
    private String status;
    private String message;
    private boolean next;
    private List<AcademyMemberListResponseDto> academyMemberListResponseDtoList;

    public static AcademyManagementResponseDto getPage(boolean next, List<AcademyMemberListResponseDto> academyMemberListResponseDtoList){
        return AcademyManagementResponseDto.builder()
                .status("200")
                .message("OK")
                .next(next)
                .academyMemberListResponseDtoList(academyMemberListResponseDtoList)
                .build();
    }
}
