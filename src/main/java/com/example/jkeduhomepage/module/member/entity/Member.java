package com.example.jkeduhomepage.module.member.entity;

import com.example.jkeduhomepage.module.common.enums.Status;
import com.example.jkeduhomepage.module.common.utility.Basetime;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="MEMBER")
public class Member extends Basetime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;

    private String password;

    private String email;

    private String name;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Status status;

}
