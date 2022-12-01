package com.example.jkedudemo.module.jwt;

import com.example.jkedudemo.module.handler.MyInternalServerException;
import com.example.jkedudemo.module.jwt.dto.TokenDto;
import com.example.jkedudemo.module.jwt.entity.RefreshToken;
import com.example.jkedudemo.module.member.service.CustomUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {
    //토큰 검증 및 생성
    private static final String AUTHORITIES_ROLE = "auth";
    private static final String BEARER_TYPE = "bearer";
    //토큰 만료시간
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
    private final Key key;

    //디코딩 메소드
    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    public TokenDto generateTokenDto(Authentication authentication,String name) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        Date tokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME*5);

        System.out.println(tokenExpiresIn);
        int idx = authorities.indexOf("_");

        String accessToken = Jwts.builder()
                //member.getId() -> getCurrentMemberId에 사용
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_ROLE,authorities.substring(idx+1))
                //member.getName -> JWT 토큰 { aud : member.getName }
                .setAudience(name)
                .setExpiration(tokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                //member.getId() -> getCurrentMemberId에 사용
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_ROLE,authorities.substring(idx+1))
                //member.getName -> JWT 토큰 { aud : member.getName }
                .setAudience(name)
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDto.builder()
                .status("200")
                .message("OK")
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_ROLE) == null) {
            throw new MyInternalServerException("권한 정보가 없는 토큰입니다.");
        }


        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_ROLE).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new CustomUser(claims.getSubject(), "", authorities,claims.getAudience());

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    //Refresh 유효성 검사
    public String validateRefreshToken(RefreshToken refreshTokenObj){

        // refresh 객체에서 refreshToken 추출
        String refreshToken = refreshTokenObj.getRefreshToken();

        try {
            // 검증
            Claims claims =parseClaims(refreshToken);

            //refresh 토큰의 만료시간이 지나지 않았을 경우, 새로운 access 토큰을 생성합니다.
            if (!claims.getExpiration().before(new Date())) {
                return recreationAccessToken(claims.getSubject(), claims.get(AUTHORITIES_ROLE).toString(),claims.getAudience());
            }
        }catch (Exception e) {

            //refresh 토큰이 만료되었을 경우, 로그인이 필요합니다.
            return null;

        }

        return null;
    }

    //액세스 토큰 재발급
    public String recreationAccessToken(String id, String auth,String name){

        // 정보는 key / value 쌍으로 저장된다.
        long now = (new Date()).getTime();
        Date tokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        //Access Token
        String accessToken = Jwts.builder()
                .setSubject(id)
                .claim(AUTHORITIES_ROLE,auth)// 정보 저장
                .setAudience(name) // 토큰 발행 시간 정보
                .setExpiration(tokenExpiresIn) // set Expire Time
                .signWith(key, SignatureAlgorithm.HS512)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        return accessToken;
    }


    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
