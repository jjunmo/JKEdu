package com.example.jkedudemo.module.member.entity;

import com.example.jkedudemo.module.member.role.RoleType;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String member_password;

    private String phone_number;

    @Enumerated(EnumType.STRING)
    private RoleType role_type;

}
