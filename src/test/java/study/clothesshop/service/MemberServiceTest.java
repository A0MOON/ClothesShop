package study.clothesshop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.clothesshop.domain.Member;
import study.clothesshop.repository.MemberRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() {
        //given
        Member member = new Member();
        member.setName("aaa");
        member.setLoginId("id1");
        member.setPassword("1234");
        Member newMember = memberRepository.save(member);
        //when
        List<Member> findmember = memberRepository.findByName("aaa");
        Member findmember1 = findmember.get(0);

        //then
        assertEquals(findmember1.getName(), newMember.getName());
    }

    @Test
    public void 로그인() {
        //given
        Member member = new Member();
        member.setLoginId("id2");
        member.setPassword("12345");
        memberRepository.save(member);

        //when
        boolean login = memberService.login("id2", "12345");

        //then
        assertEquals(login, true);

    }

    @Test
    public void 아이디찾기() {
        //given
        Member member = new Member();
        member.setLoginId("id3");
        member.setPassword("123456");
        member.setName("a1");
        member.setEmail("a@a");
        Member save = memberRepository.save(member);

        //when
        String a = memberService.findLoginIdByNameAndEmail("a1", "a@a");
        //then
        assertEquals(a, save.getLoginId());
    }

    @Test
    public void 회원탈퇴() {
        // given
        Member member = new Member();
        member.setLoginId("id4");
        member.setName("a4");
        member.setPassword("password4");
        memberRepository.save(member);

        // When
        memberService.withdraw(member.getId());

        // Then
        assertFalse(memberRepository.existsById(member.getId()));
    }

    @Test
    @Rollback(value = false)
    void 이메일정보수정() {
        // Given
        Member member = new Member();
        member.setLoginId("testuser5");
        member.setName("Test User5");
        member.setEmail("oldemail@example.com");
        memberRepository.save(member);

        // When
        memberService.updateEmail(member.getId(), "newemail@example.com");

        // Then
        Member updatedMember = memberRepository.findById(member.getId()).orElse(null);
        assertNotNull(updatedMember);
        assertEquals("newemail@example.com", updatedMember.getEmail());
    }

    @Test
    @Rollback(value = false)
    void 휴대폰정보수정() {
        // Given
        Member member = new Member();
        member.setLoginId("testuser6");
        member.setName("Test User6");
        member.setPhone("1234567890");
        memberRepository.save(member);

        // When
        memberService.updatePhoneNumber(member.getId(), "0987654321");

        // Then
        Member updatedMember = memberRepository.findById(member.getId()).orElse(null);
        assertNotNull(updatedMember);
        assertEquals("0987654321", updatedMember.getPhone());
    }




}
