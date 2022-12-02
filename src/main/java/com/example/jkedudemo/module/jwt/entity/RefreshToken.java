package com.example.jkedudemo.module.jwt.entity;

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


    @Column(name = "KEY_ID", nullable = false)
    private String keyId;

    @Column(name ="USER_AGENT", nullable = false)
    private String userAgent;
}
