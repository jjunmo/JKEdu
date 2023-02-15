package com.example.jkeduhomepage.module.member.repository;

import com.example.jkeduhomepage.module.common.enums.YN;
import com.example.jkeduhomepage.module.member.entity.MemberPhoneAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberPhoneAuthRepository extends JpaRepository<MemberPhoneAuth,Long> {

    Optional<MemberPhoneAuth> findByPhoneAndCheckYn(String phone, YN yn);
}
