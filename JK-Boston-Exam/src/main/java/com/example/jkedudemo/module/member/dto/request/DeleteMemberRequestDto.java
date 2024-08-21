package com.example.jkedudemo.module.member.dto.request;

import com.example.jkedudemo.module.common.enums.member.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeleteMemberRequestDto {
    private String password;
    private Status status;
}
