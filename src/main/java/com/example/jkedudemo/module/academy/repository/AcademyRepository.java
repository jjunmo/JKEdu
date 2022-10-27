package com.example.jkedudemo.module.academy.repository;

import com.example.jkedudemo.module.academy.entity.AcademyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademyRepository extends JpaRepository<AcademyMember,Long> {
}
