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
public class MemberNameResopnseDto {

    public String status;
    public String message;
    public String name;

    public static MemberNameResopnseDto name(Member member){
        return MemberNameResopnseDto.builder()
                .status("200")
                .message("OK")
                .name(member.getName())
                .build();
    }
}
