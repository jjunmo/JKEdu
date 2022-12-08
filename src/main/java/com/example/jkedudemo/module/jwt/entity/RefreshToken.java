package com.example.jkedudemo.module.jwt.entity;

import com.example.jkedudemo.module.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "REFRESH_TOKEN")
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REFRESH_TOKEN_ID", nullable = false)
    private Long id;

    @Column(name = "REFRESH_TOKEN", nullable = false)
    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member keyId;

    @Column(name ="USER_AGENT", nullable = false)
    private String userAgent;
}
