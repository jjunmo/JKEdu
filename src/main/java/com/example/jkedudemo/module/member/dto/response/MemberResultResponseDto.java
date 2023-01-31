package com.example.jkedudemo.module.member.dto.response;


import com.example.jkedudemo.module.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberResultResponseDto {
    private String status;
    private String message;
    private boolean next;
    private String name;

    List<ResultListDto> resultListDtoList;


    public static MemberResultResponseDto toDto(Member member, boolean next, List<ResultListDto> resultListDtoList){
        List<ResultListDto> resultList = new ArrayList<>();
        resultListDtoList.forEach(
                rld -> {
                    ResultListDto resultListDto=new ResultListDto();
                    resultListDto.setExamDate(rld.getExamDate());
                    resultListDto.setExamId(rld.getExamId());
                    resultList.add(resultListDto);
                });

        return MemberResultResponseDto.builder()
                .status("200")
                .message("OK")
                .next(next)
                .name(member.getName())
                .resultListDtoList(resultList)
                .build();
    }
}
