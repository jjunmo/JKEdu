package com.example.jkedudemo.module.member.entity;

import com.example.jkedudemo.module.common.enums.Level;
import com.example.jkedudemo.module.common.util.BaseTime;
import com.example.jkedudemo.module.common.enums.member.Role;
import com.example.jkedudemo.module.common.enums.member.Status;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

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

    //@Column(nullable = false)
    private String email;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date birth;

   // @Column(nullable = false)
    private String password;

    //@Column(nullable = false)
    private String phone;

    private String academyId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'GREEN'")
    private Status status;

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
