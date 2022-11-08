package com.example.jkedudemo.module.member.dto.response;

import com.example.jkedudemo.module.member.entity.Member;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class HttpStatusResopnse {

    private String status = "200";
    private String message = "OK";

}
