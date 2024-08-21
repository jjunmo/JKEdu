package com.example.jkedudemo.module.member.entity;

import com.example.jkedudemo.module.common.enums.member.Role;
import com.example.jkedudemo.module.common.enums.member.Status;
import com.example.jkedudemo.module.common.utility.BaseTime;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "MEMBER")
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("아이디")
    private String email;

    @Comment("사용자 이름")
    private String name;

    @Temporal(TemporalType.DATE)
    @Comment("생년월일")
    private Date birth;

    @Comment("비밀번호")
    private String password;

    @Comment("휴대폰 번호")
    private String phone;

    @Column(name = "code")
    private String academyId;

    @Enumerated(EnumType.STRING)
    @Comment("멤버 권한")
    private Role role;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'GREEN'")
    @Comment("멤버 상태")
    private Status status;

    @Comment("테스트 횟수")
    private int testCount;

//    private int levelScore=0;

    //학원 학생
    public Member(String phone, String name, Date birth, Role role, String academyId,Status status) {
        this.phone=phone;
        this.name=name;
        this.birth=birth;
        this.role=role;
        this.academyId=academyId;
        this.status=status;
    }



    //TODO: 약관동의 체크

}
