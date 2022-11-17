package com.example.jkedudemo.module.member.entity;

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
@DynamicInsert
@Table(name = "member")
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

    @ColumnDefault("0")
    private Integer testCount;

    //TODO: 약관동의 체크

}
