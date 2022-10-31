package com.example.jkedudemo.module.member.entity;

import com.example.jkedudemo.module.common.BaseTime;
import com.example.jkedudemo.module.common.enums.PhoneAuthType;
import com.example.jkedudemo.module.common.enums.YN;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "member_phone_auth")
public class MemberPhoneAuth extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Member member;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private PhoneAuthType phoneAuthType;

    @Enumerated(EnumType.STRING)
    private YN checkYn;

    //인증 코드
    private String code;

}
