package com.example.jkedudemo.module.member.service;

import com.example.jkedudemo.module.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;


@Getter
@Setter
public class CustomUser extends User {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    // 유저의 정보를 더 추가하고 싶다면 이곳과, 아래의 생성자 파라미터를 조절해야 한다.
    private Member member;
    private String name;


    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities,String name) {
        super(username, password, authorities);
        this.name=name;
    }

}
