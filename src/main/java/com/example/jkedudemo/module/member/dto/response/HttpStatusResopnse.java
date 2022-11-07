package com.example.jkedudemo.module.member.dto.response;

import com.example.jkedudemo.module.member.entity.Member;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class HttpStatusResopnse {

    private String status;
    private String message;

}
