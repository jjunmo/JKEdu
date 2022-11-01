package com.example.jkedudemo.module.member.entity;

import com.example.jkedudemo.module.common.BaseTime;
import com.example.jkedudemo.module.common.enums.RoleType;
import com.example.jkedudemo.module.common.enums.Status;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

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

   // @Column(nullable = false)
    private String memberPassword;

    //@Column(nullable = false)
    private String phoneNumber;

    // TODO: RoleType -> ROLE_TEACHER_STUDENT ( academyId ) 저장
    private String academyId;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'GREEN'")
    private Status status;

}
