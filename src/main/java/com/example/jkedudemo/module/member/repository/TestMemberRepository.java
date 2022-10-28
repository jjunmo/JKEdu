package com.example.jkedudemo.module.member.repository;

import com.example.jkedudemo.module.member.entity.TestMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestMemberRepository extends JpaRepository<TestMember,Long> {
}
