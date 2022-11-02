package com.example.jkedudemo.module.member.dto.request;

import com.example.jkedudemo.module.common.enums.RoleType;
import com.example.jkedudemo.module.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AcademyMemberRequestDto {
    private String name;

    private Date birth;

    private String phoneNumber;

    private RoleType roleType;

    private String academyId;
}
