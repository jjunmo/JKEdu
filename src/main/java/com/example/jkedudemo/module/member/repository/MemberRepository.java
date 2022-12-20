package com.example.jkedudemo.module.member.repository;

import com.example.jkedudemo.module.common.enums.member.Role;
import com.example.jkedudemo.module.common.enums.member.Status;
import com.example.jkedudemo.module.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByAcademyId(String academyId);

    Optional<Member> findByEmailAndStatusIn(String email , Collection<Status> status);

    boolean existsByPhoneAndStatus(String phone , Status status);
    Optional<Member> findByPhoneAndStatusIn(String phone, Collection<Status> status);

    Member findByRoleAndAcademyId(Role role,String academyId);

    //학원 학생 리스트 조회
    Optional<Member> findByPhoneAndRoleAndAcademyIdAndStatus(String phone , Role role , String academyId ,Status status);

    Page<Member> findByAcademyIdAndRoleAndStatusOrderByIdAsc(String academyId,Role role,Status status, Pageable pageable);

    Page<Member> findByAcademyIdAndRoleAndStatusAndNameContainingOrderByIdAsc(String academyId,Role role,Status status,String naming, Pageable pageable);

}
