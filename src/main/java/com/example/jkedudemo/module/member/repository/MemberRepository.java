package com.example.jkedudemo.module.member.repository;

import com.example.jkedudemo.module.common.enums.Status;
import com.example.jkedudemo.module.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByPhoneNumberAndStatus(String phoneNumber , Status status);
    boolean existsByEmail(String email);



    Optional<Member> findByPhoneNumberAndStatusIn(String phoneNumber, Collection<Status> status);
}
