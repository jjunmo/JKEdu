package com.example.jkedudemo.module.member.repository;

import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.common.enums.member.PhoneAuth;
import com.example.jkedudemo.module.member.entity.MemberPhoneAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberPhoneAuthRepository extends JpaRepository<MemberPhoneAuth,Long> {

    Optional<MemberPhoneAuth> findByPhoneAndPhoneauth(String phone, PhoneAuth phoneauth);

    Optional<MemberPhoneAuth> findByPhoneAndSmscodeAndPhoneauth(String phone, String smscode , PhoneAuth phoneauth);

    Optional<MemberPhoneAuth> findByPhoneAndCheckYnAndPhoneauth(String phone, YN checkYN, PhoneAuth phoneauth);

    List<MemberPhoneAuth> findByPhone (String phone);

}
