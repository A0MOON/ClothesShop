package study.clothesshop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.clothesshop.domain.Member;
import study.clothesshop.repository.MemberRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("aaa");
        member.setLoginId("id");
        member.setPassword("1234");
        Member newMember = memberRepository.save(member);
        //when
        List<Member> findmember = memberRepository.findByName("aaa");
        Member findmember1 = findmember.get(0);

        //then
        assertEquals(findmember1.getName(), newMember.getName());
     }

     @Test
     public void 로그인(){
         //given
         Member member = new Member();
         member.setLoginId("id");
         member.setPassword("1234");
         Member newMember = memberRepository.save(member);

         //when
         boolean login = memberService.login("id", "1234");

         //then
         assertEquals(login,true);

      }



}
