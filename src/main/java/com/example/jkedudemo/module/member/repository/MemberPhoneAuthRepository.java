package com.example.jkedudemo.module.member.repository;

import com.example.jkedudemo.module.common.enums.PhoneAuthType;
import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.member.entity.Member;
import com.example.jkedudemo.module.member.entity.MemberPhoneAuth;
import org.hibernate.mapping.Join;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberPhoneAuthRepository extends JpaRepository<MemberPhoneAuth,Long> {

    Optional<MemberPhoneAuth> findByPhoneNumberAndPhoneAuthType(String phoneNumber, PhoneAuthType phoneAuthSort);

    Optional<MemberPhoneAuth> findByPhoneNumberAndCodeAndPhoneAuthType(String phoneNumber, String code , PhoneAuthType phoneAuthType);

    Optional<MemberPhoneAuth> findByPhoneNumberAndCheckYnAndPhoneAuthType(String phoneNumber, YN checkYN, PhoneAuthType phoneAuthType);
}
