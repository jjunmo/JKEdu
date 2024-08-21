package com.example.jkeduhomepage.module.member.repository;

import com.example.jkeduhomepage.module.common.enums.Role;
import com.example.jkeduhomepage.module.common.enums.Status;
import com.example.jkeduhomepage.module.common.enums.YN;
import com.example.jkeduhomepage.module.member.entity.Member;
import com.example.jkeduhomepage.module.member.entity.MemberPhoneAuth;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberPhoneAuthRepository memberPhoneAuthRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("save Member")
    public void testMember(){
        MemberPhoneAuth paramMemberPhoneAuth=new MemberPhoneAuth(null,"12341234", YN.Y,"1111");
        MemberPhoneAuth memberPhoneAuth=memberPhoneAuthRepository.save(paramMemberPhoneAuth);
        Member paramMember=new Member(null,"aaaa","a123456","a@a","momo","12341234",Status.RED, Role.ROLE_USER,memberPhoneAuth);
        Member member =memberRepository.save(paramMember);

        assertEquals(member.getId(),paramMember.getId());
        assertEquals(member.getLoginId(),paramMember.getLoginId());
        assertEquals(member.getPassword(),paramMember.getPassword());
        assertEquals(member.getEmail(),paramMember.getEmail());
        assertEquals(member.getName(),paramMember.getName());
        assertEquals(member.getPhone(),paramMember.getPhone());
        assertEquals(member.getStatus(),paramMember.getStatus());
        assertEquals(member.getMemberPhoneAuth(),memberPhoneAuth);
        assertEquals(member,paramMember);
        print();

    }
}