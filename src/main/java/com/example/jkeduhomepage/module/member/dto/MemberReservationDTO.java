package com.example.jkeduhomepage.module.member.dto;

import com.example.jkeduhomepage.module.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
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

    public static MemberReservationDTO paramReservation(MemberReservationDTO memberReservationDTO){
        return MemberReservationDTO.builder()
                .day(memberReservationDTO.getDay())
                .name(memberReservationDTO.getName())
                .school(memberReservationDTO.getSchool())
                .grade(memberReservationDTO.getGrade())
                .phone(memberReservationDTO.getPhone())
                .relationship(memberReservationDTO.getRelationship())
                .build();
    }


}
