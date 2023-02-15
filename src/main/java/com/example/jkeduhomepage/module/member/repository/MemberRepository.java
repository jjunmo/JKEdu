package com.example.jkeduhomepage.module.member.repository;

import com.example.jkeduhomepage.module.common.enums.Status;
import com.example.jkeduhomepage.module.common.enums.YN;
import com.example.jkeduhomepage.module.member.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByLoginIdAndStatus(String loginId, Status status);

    Optional<Member> findByPhone(String phone);

}
