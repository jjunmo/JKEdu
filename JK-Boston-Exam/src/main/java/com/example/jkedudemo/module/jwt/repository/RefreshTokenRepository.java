package com.example.jkedudemo.module.jwt.repository;

import com.example.jkedudemo.module.jwt.entity.RefreshToken;
import com.example.jkedudemo.module.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    List<RefreshToken> findByKeyIdAndUserAgent(Member keyId, String userAgent);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);



}
