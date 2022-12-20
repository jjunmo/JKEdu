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
public class ManagementResponseDto {
    private String status;
    private String message;
    private int allPage;
    private List<AcademyMemberListResponseDto> academyMemberListResponseDtoList;

    public static ManagementResponseDto getPage(int allPage, List<AcademyMemberListResponseDto> academyMemberListResponseDtoList){
        return ManagementResponseDto.builder()
                .status("200")
                .message("OK")
                .allPage(allPage)
                .academyMemberListResponseDtoList(academyMemberListResponseDtoList)
                .build();
    }
}
