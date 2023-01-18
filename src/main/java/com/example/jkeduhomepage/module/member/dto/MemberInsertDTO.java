package com.example.jkeduhomepage.module.member.dto;

import com.example.jkeduhomepage.module.common.enums.Status;
import lombok.Data;

@Data
public class MemberInsertDTO {

    private String loginId;

    private String password;

    private String email;

    private String name;

    private String phone;

    private Status status;

}
