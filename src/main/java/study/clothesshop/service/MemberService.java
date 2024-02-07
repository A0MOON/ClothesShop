package study.clothesshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.clothesshop.domain.Member;
import study.clothesshop.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    // 회원 가입
    public Long join(Member member) {

        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional(readOnly = true)
    public void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    @Transactional(readOnly = true)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }


    // 아이디 조회
    @Transactional(readOnly = true)
    public String findLoginIdByNameAndEmail(String name, String email) {
        Optional<Member> loginIdByNameAndEmail = memberRepository.findLoginIdByNameAndEmail(name, email);
        if (loginIdByNameAndEmail.isPresent()) {
            Member member = loginIdByNameAndEmail.get();
            return member.getLoginId();
        }
        return null; // 회원이 존재하지 않는 경우 null 반환
    }

    // 로그인
    public boolean login(String loginId, String password) {

        Member member = memberRepository.findByLoginId(loginId);

        if (member != null && member.getPassword().equals(password)) {
            return true;
        }
        return false;

    }


    // 비밀번호 변경
    @Transactional
    public void changePassword(String loginId, String name, String email, String newPassword) {
        Member member = memberRepository.findPasswordByLoginIdAndNameAndEmail(loginId, name, email);
        if (member == null) {
            throw new IllegalArgumentException("해당하는 회원이 없습니다.");
        }
        member.changePassword(newPassword);
    }


}
