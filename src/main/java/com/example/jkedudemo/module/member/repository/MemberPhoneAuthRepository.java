package com.example.jkedudemo.module.member.repository;

import com.example.jkedudemo.module.common.enums.PhoneAuthType;
import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.member.entity.MemberPhoneAuth;
import org.hibernate.mapping.Join;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberPhoneAuthRepository extends JpaRepository<MemberPhoneAuth,Long> {

    Optional<MemberPhoneAuth> findByPhoneAndPhoneAuthType(String phone, PhoneAuthType phoneAuthType);

    Optional<MemberPhoneAuth> findByPhoneAndSmscodeAndPhoneAuthType(String phone, String smscode , PhoneAuthType phoneAuthType);

    Optional<MemberPhoneAuth> findByPhoneAndCheckYnAndPhoneAuthType(String phone, YN checkYN, PhoneAuthType phoneAuthType);

    List<MemberPhoneAuth> findByPhone (String phone);
}
