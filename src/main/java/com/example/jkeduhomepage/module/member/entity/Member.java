package com.example.jkeduhomepage.module.member.entity;

import com.example.jkeduhomepage.module.common.enums.Role;
import com.example.jkeduhomepage.module.common.enums.Status;
import com.example.jkeduhomepage.module.common.utility.Basetime;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;


@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="MEMBER")
public class Member extends Basetime {

    @Id
    @Comment("회원 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("로그인 아이디")
    @Column(nullable = false)
    private String loginId;

    @Comment("비밀번호")
    @Column(nullable = false)
    private String password;

    @Comment("이메일")
    @Column(nullable = false)
    private String email;

    @Comment("이름")
    @Column(nullable = false)
    private String name;

    @Comment("연락처")
    @Column(nullable = false)
    private String phone;

    @Comment("승인 상태")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Comment("유저 권한")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Comment("휴대폰 인증 여부")
    @OneToOne
    @JoinColumn(name = "memberPhoneAuth")
    private MemberPhoneAuth memberPhoneAuth;

}
