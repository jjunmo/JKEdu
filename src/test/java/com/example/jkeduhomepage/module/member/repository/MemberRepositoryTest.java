package com.example.jkeduhomepage.module.member.repository;

import com.example.jkeduhomepage.module.common.enums.Role;
import com.example.jkeduhomepage.module.common.enums.Status;
import com.example.jkeduhomepage.module.member.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("save Member")
    public void testMember(){
        Member member=new Member(null,"aaaa","a123456","a@a","momo","12341234", Status.GREEN, Role.ROLE_USER);
        Member member1 =memberRepository.save(member);

        assertEquals(member.getId(),member1.getId());
        assertEquals(member.getLoginId(),member1.getLoginId());
        assertEquals(member.getPassword(),member1.getPassword());
        assertEquals(member.getEmail(),member1.getEmail());
        assertEquals(member.getName(),member1.getName());
        assertEquals(member.getPhone(),member1.getPhone());
        assertEquals(member.getStatus(),member1.getStatus());
        assertEquals(member,member1);

    }
}