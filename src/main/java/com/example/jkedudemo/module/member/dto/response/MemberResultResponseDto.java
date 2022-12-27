package com.example.jkedudemo.module.member.dto.response;

import com.example.jkedudemo.module.exam.dto.response.ExamResultLevelDto;
import com.example.jkedudemo.module.exam.dto.response.ExamResultResponseDto;
import com.example.jkedudemo.module.exam.dto.response.ExamineeInfoResponseDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResultResponseDto {
    private String status;
    private String message;
    private boolean next;

    List<ResultListDto> resultListDtoList;


    public static MemberResultResponseDto toDto(boolean next,List<ResultListDto> resultListDtoList){
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
                .resultListDtoList(resultList)
                .build();
    }
}
