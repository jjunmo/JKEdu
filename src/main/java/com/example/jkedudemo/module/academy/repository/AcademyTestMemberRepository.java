package com.example.jkedudemo.module.academy.repository;

import com.example.jkedudemo.module.academy.entity.AcademyTestMember;
import org.springframework.data.jpa.repository.JpaRepository;

@Deprecated
public interface AcademyTestMemberRepository extends JpaRepository<AcademyTestMember,Long> {
}
