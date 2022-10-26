package com.example.jkdeudemo.module.test.repository;

import com.example.jkdeudemo.module.test.entity.TestMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestMember,Long> {
}
