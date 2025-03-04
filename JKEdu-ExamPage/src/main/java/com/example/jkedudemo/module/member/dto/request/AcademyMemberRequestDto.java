package com.example.jkedudemo.module.member.dto.request;

import com.example.jkedudemo.module.common.enums.member.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AcademyMemberRequestDto {
    private String name;

    private Date birth;

    private String phone;

    private Role role;

    private String academyId;

    public void setRole(String roletype) {
        this.role = Role.valueOf("ROLE_"+roletype.toUpperCase());
    }

    public Role getRole() {
        return role;
    }
}
