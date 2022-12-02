package com.example.jkedudemo.module.jwt.repository;

import com.example.jkedudemo.module.jwt.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByKeyIdAndUserAgent(String keyId,String userAgent);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);



}
