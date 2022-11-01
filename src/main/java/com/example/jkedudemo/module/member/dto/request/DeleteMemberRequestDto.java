package com.example.jkedudemo.module.member.dto.request;

import com.example.jkedudemo.module.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteMemberRequestDto {
    private String password;
    private Status status;
}
