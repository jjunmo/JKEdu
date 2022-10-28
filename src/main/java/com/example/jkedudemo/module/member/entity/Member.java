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
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(nullable = false)
    private String email;

   // @Column(nullable = false)
    private String member_password;

    //@Column(nullable = false)
    private String phone_number;

    @Enumerated(EnumType.STRING)
    private RoleType role_type;

}
