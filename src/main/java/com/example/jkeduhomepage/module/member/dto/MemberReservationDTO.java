package com.example.jkeduhomepage.module.member.dto;

import com.example.jkeduhomepage.module.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberReservationDTO {

    private Date day;
    private String name;
    private String school;
    private String grade;
    private String phone;
    private String relationship;
//
//    public static MemberResponseDTO choiceMember(Member member){
//        return MemberResponseDTO.builder()
//                .loginId(member.getLoginId())
//                .email(member.getEmail())
//                .name(member.getName())
//                .phone(member.getPhone())
//                .status(String.valueOf(member.getStatus()))
//                .role(String.valueOf(member.getRole()))
//                .createdDate(String.valueOf(member.getCreateDate()))
//                .updatedDate(String.valueOf(member.getUpdateDate()))
//                .build();
//    }
}
