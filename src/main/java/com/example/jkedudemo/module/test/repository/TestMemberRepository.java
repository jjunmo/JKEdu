package com.example.jkedudemo.module.test.repository;

import com.example.jkedudemo.module.test.entity.TestMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestMemberRepository extends JpaRepository<TestMember,Long> {
}
