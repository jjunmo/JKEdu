package com.example.jkedudemo.module.member.repository;

import com.example.jkedudemo.module.common.enums.PhoneAuthSort;
import com.example.jkedudemo.module.member.entity.MemberPhoneAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberPhoneAuthRepository extends JpaRepository<MemberPhoneAuth,Long> {

    Optional<MemberPhoneAuth> findByPhoneNumberAndPhoneAuthSort(String phoneNumber, PhoneAuthSort phoneAuthSort);
}
