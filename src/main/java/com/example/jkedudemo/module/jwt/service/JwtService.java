package com.example.jkedudemo.module.jwt.service;

import com.example.jkedudemo.module.config.SecurityUtil;
import com.example.jkedudemo.module.handler.MyForbiddenException;
import com.example.jkedudemo.module.jwt.TokenProvider;
import com.example.jkedudemo.module.jwt.dto.TokenDto;
import com.example.jkedudemo.module.jwt.entity.RefreshToken;
import com.example.jkedudemo.module.jwt.repository.RefreshTokenRepository;
import com.example.jkedudemo.module.member.dto.response.MemberStatusOkResponseDto;
import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    public Member isMemberCurrent() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new MyForbiddenException("로그인 유저 정보가 없습니다"));
    }

    /**
     * refreshToken 유효성 검증
     * @param refreshToken refreshToken
     * @return access Token
     */
    public Map<String, String> validateRefreshToken(String refreshToken) {
        Optional<RefreshToken> refreshTokenOptional =refreshTokenRepository.findByRefreshToken(refreshToken);
        if(refreshTokenOptional.isPresent()) {
            RefreshToken refresh = refreshTokenOptional.get();
            String createdAccessToken = tokenProvider.validateRefreshToken(refresh);
            return createRefreshJson(createdAccessToken);
        }

        return Map.of("refreshToken",refreshToken);
    }

    /**
     * accessToken 재발급
     * @param createdAccessToken access 토큰 재발급
     * @return accessToken
     */
    public Map<String, String> createRefreshJson(String createdAccessToken){

        Map<String, String> map = new HashMap<>();

        if(createdAccessToken == null){
            map.put("errortype", "refresh_expired");
            map.put("status", "401");
            map.put("message", "Refresh 토큰이 만료되었습니다. 로그인이 필요합니다.");

            return map;
        }
        //기존에 존재하는 accessToken 제거
        map.put("status", "200");
        map.put("message", "Refresh 토큰을 통한 Access Token 생성이 완료되었습니다.");
        map.put("accessToken", createdAccessToken);

        return map;

    }

    /**
     * 새로고침
     * @param userAgent 로그인 한 Browser
     * @return login
     */
    public TokenDto refresh(String userAgent) {
        Member member = isMemberCurrent();
        List<RefreshToken> refreshTokenList = refreshTokenRepository.findByKeyIdAndUserAgent(member, userAgent);

        if(refreshTokenList==null| Objects.requireNonNull(refreshTokenList).isEmpty()){
            throw new MyForbiddenException("로그인을 다시 해주세요.");
        }else{
            RefreshToken refresh = refreshTokenList.get(refreshTokenList.size()-1);
            String createdAccessToken = tokenProvider.validateRefreshToken(refresh);
            return new TokenDto("200", "OK",createdAccessToken, refresh.getRefreshToken());
        }
    }

    /**
     * 로그아웃
     * @param userAgent 로그아웃 하는 Browser
     * @return refrsehToken 삭제
     */
    @Transactional
    public MemberStatusOkResponseDto logout(String userAgent) {
        Member member = isMemberCurrent();

        List<RefreshToken> refreshTokenList = refreshTokenRepository.findByKeyIdAndUserAgent(member, userAgent);

        log.info(refreshTokenList.get(0).getRefreshToken()+ "refreshToken!!!");

        if (Objects.requireNonNull(refreshTokenList,"refreshToken 없습니다 !!").isEmpty()) {
            throw new MyForbiddenException("잘못된 접근입니다");
        } else {
            refreshTokenRepository.deleteAll(refreshTokenList);
            return MemberStatusOkResponseDto.statusOk();
        }
    }

}
