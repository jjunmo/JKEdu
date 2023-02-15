package com.example.jkeduhomepage.module.member.entity;

import com.example.jkeduhomepage.module.common.enums.YN;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "MEMBER_PHONE_AUTH")
public class MemberPhoneAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("인증요청 번호")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "Y_N")
    @Comment("인증 여부")
    private YN checkYn;

    //인증 코드
    @Comment("인증 코드")
    private String smscode;




}
