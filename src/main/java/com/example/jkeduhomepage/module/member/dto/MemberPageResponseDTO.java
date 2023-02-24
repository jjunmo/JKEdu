package com.example.jkeduhomepage.module.member.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberPageResponseDTO {

    boolean next;

    private List<MemberResponseDTO> memberResponseDTOList;


    public static MemberPageResponseDTO getPage(boolean next, List<MemberResponseDTO> memberResponseDTOList){
        return MemberPageResponseDTO.builder()
                .next(next)
                .memberResponseDTOList(memberResponseDTOList)
                .build();
    }
}
