package com.example.jkedudemo.module.academy.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AcademyMember {

    @Id
    private Long id;

    private String academy_code;

    private String academy_password;

    private String phone_number;

    @OneToMany
    private List<AcademyTestMember> academyTestMemberList = new ArrayList<>();

}
