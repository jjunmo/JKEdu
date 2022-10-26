package com.example.jkdeudemo.module.member.repository;

import com.example.jkdeudemo.module.member.entity.member.StudentMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentMember,Long> {
}
