package com.example.jkedudemo.module.member.entity;

import com.example.jkedudemo.module.common.BaseTime;
import com.example.jkedudemo.module.common.enums.RoleType;
import com.example.jkedudemo.module.common.enums.Status;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "member")
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(nullable = false)
    private String email;

   // @Column(nullable = false)
    private String member_password;

    //@Column(nullable = false)
    private String phoneNumber;

    // TODO: RoleType -> ROLE_TEACHER_STUDENT ( TEACHER_id ) 저장
    private Long teacherId;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    private Status status;

}
