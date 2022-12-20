package com.example.jkedudemo.module.member.dto.response;

import com.example.jkedudemo.module.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcademyMemberListResponseDto {
    String name;
    Date birth;
    String phone;

    public static AcademyMemberListResponseDto find(Member member){
        return AcademyMemberListResponseDto.builder()
                .name(member.getName())
                .birth(member.getBirth())
                .phone(member.getPhone())
                .build();
    }
}
