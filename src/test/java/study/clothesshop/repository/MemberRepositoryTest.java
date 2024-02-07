package study.clothesshop.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.clothesshop.domain.Member;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 아이디_조회() {
        //given
        Member member1 = new Member();
        member1.setLoginId("member1");
        member1.setEmail("member1@example.com");
        member1.setName("멤버");
        memberRepository.save(member1);
        //when
        Optional<Member> findMember = memberRepository.findLoginIdByNameAndEmail("멤버", "member1@example.com");
        //then
        assertTrue(findMember.isPresent());
        Member findLoginId = findMember.get();
        assertEquals(findLoginId.getLoginId(), "member1");
    }

    @Test
    public void 비밀번호변경() {
        //given
        Member member1 = new Member();
        member1.setLoginId("member1");
        member1.setEmail("member1@example.com");
        member1.setName("멤버");
        member1.setPassword("qwert");
        Member member3 = memberRepository.save(member1);
        //when
        Optional<Member> loginId = memberRepository.findByNameAndEmailAndLoginIdAndPassword("멤버", "member1@example.com", "member1", "qwert");
        Member member2 = loginId.get();
        assertEquals(member3,member2);

        member2.setPassword("trewq");
        //then
        assertEquals(member3.getPassword(),member2.getPassword());

    }


    @Test
    public void 로그인() {
        //given
        Member member1 = new Member();
        member1.setLoginId("member1");
        member1.setPassword("qwert");

        //when
        Member save = memberRepository.save(member1);
        Optional<Member> login = memberRepository.findByLoginIdAndPassword("member1", "qwert");
        Member login1 = login.get();
        //then
        assertEquals(save, login1);
        assertEquals(login1.getLoginId(), save.getLoginId());
        assertEquals(login1.getPassword(), save.getPassword());
    }


}
