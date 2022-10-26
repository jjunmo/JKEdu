package com.example.jkdeudemo.module.member.repository;

import com.example.jkdeudemo.module.member.entity.member.AcademyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademyRepository extends JpaRepository<AcademyMember,Long> {
}
