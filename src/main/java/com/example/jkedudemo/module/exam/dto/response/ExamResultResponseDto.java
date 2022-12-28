package com.example.jkedudemo.module.exam.dto.response;

import lombok.*;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExamResultResponseDto {
    String status;
    String message;
    ExamineeInfoResponseDto examineeInfoResponseDto;
    int examResultCount;
    List<ExamResultLevelDto> examResultLevelDtoList;


    public static ExamResultResponseDto toDto(ExamineeInfoResponseDto examineeInfoResponseDto,List<ExamResultLevelDto> examResultLevelDtoList){
//        List<ExamResultLevelDto> LevelList = new ArrayList<>();
//        examResultLevelDtoList.forEach(
//                erld -> {
//                    ExamResultLevelDto examResultLevelDto = new ExamResultLevelDto();
//                    examResultLevelDto.setCategory(erld.getCategory());
//                    examResultLevelDto.setCorrectCount(erld.getCorrectCount());
//                    examResultLevelDto.setProblemCount(erld.getCategory().getValue());
//                    examResultLevelDto.setLevel(erld.getLevel());
//                    LevelList.add(examResultLevelDto);
//                });

        return ExamResultResponseDto.builder()
                .status("200")
                .message("OK")
                .examineeInfoResponseDto(examineeInfoResponseDto)
                .examResultCount(examResultLevelDtoList.size())
                .examResultLevelDtoList(examResultLevelDtoList)
                .build();
    }

}
