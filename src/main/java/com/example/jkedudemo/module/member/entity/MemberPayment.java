package com.example.jkedudemo.module.member.entity;

import com.example.jkedudemo.module.common.util.BaseTime;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "member_payment")
public class MemberPayment  extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Member member;


}
