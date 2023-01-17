package com.example.jkedudemo.module.member.entity;

import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.common.enums.member.PhoneAuth;
import com.example.jkedudemo.module.common.utility.BaseTime;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "MEMBER_PHONE_AUTH")
public class MemberPhoneAuth extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER")
    @Comment("사용자")
    private Member member;
    @Comment("인증요청 번호")
    private String phone;

    //JOIN , ID ,PW
    @Enumerated(EnumType.STRING)
    @Comment("인증요청 유형")
    private PhoneAuth phoneauth;

    //인증 여부
    @Enumerated(EnumType.STRING)
    @Column(name = "Y_N")
    @Comment("인증 여부")
    private YN checkYn;

    //인증 코드
    @Comment("인증 코드")
    private String smscode;

}
