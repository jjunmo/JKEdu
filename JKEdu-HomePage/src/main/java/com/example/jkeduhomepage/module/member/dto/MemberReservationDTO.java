package com.example.jkeduhomepage.module.member.dto;

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


}
