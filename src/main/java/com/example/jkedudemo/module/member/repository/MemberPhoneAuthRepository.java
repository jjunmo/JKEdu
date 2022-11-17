package com.example.jkedudemo.module.member.repository;

import com.example.jkedudemo.module.common.enums.member.Phoneauth;
import com.example.jkedudemo.module.common.enums.YN;
import com.example.jkedudemo.module.member.entity.MemberPhoneAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberPhoneAuthRepository extends JpaRepository<MemberPhoneAuth,Long> {

    Optional<MemberPhoneAuth> findByPhoneAndPhoneauth(String phone, Phoneauth phoneauth);

    Optional<MemberPhoneAuth> findByPhoneAndSmscodeAndPhoneauth(String phone, String smscode , Phoneauth phoneauth);

    Optional<MemberPhoneAuth> findByPhoneAndCheckYnAndPhoneauth(String phone, YN checkYN, Phoneauth phoneauth);

    List<MemberPhoneAuth> findByPhone (String phone);

}
