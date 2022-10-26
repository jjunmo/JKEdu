package com.example.jkdeudemo.module.member.entity.member;

import com.example.jkdeudemo.module.test.entity.AcademyStudent;
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

    private String email;

    private String password;

    private String academyCode;

    @OneToMany
    private List<AcademyStudent> academyStudents = new ArrayList<>();
}
