package com.example.jkedudemo.module.test.repository;

import com.example.jkedudemo.module.test.entity.AcademyTestMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademyTestMemberRepository extends JpaRepository<AcademyTestMember,Long> {
}
