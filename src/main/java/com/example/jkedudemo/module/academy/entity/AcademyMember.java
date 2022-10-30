package com.example.jkedudemo.module.academy.entity;


import com.example.jkedudemo.module.common.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Deprecated
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AcademyMember {

    @Id
    private Long id;

    private String email;

    private String academy_password;

    private String phone_number;

    @Enumerated(EnumType.STRING)
    private RoleType role_type;


    @OneToMany
    private List<AcademyTestMember> academyTestMemberList = new ArrayList<>();

}
