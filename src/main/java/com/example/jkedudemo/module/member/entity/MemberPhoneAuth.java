package com.example.jkedudemo.module.member.entity;

import com.example.jkedudemo.module.common.util.BaseTime;
import com.example.jkedudemo.module.common.enums.Phoneauth;
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
    @JoinColumn(name = "member")
    private Member member;

    private String phone;

    //JOIN , ID ,PW
    @Enumerated(EnumType.STRING)
    private Phoneauth phoneauth;

    //인증 여부
    @Enumerated(EnumType.STRING)
    private YN checkYn;

    //인증 코드
    private String smscode;



}
