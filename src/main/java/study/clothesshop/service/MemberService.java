package study.clothesshop.service;

import lombok.RequiredArgsConstructor;

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

    public void save(Member member) {
        memberRepository.save(member);
    }

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
    // 아이디 조회
    @Transactional(readOnly = true)
    public String findLoginIdByNameAndEmail(String name, String email) {
        Optional<Member> loginIdByNameAndEmail = memberRepository.findLoginIdByNameAndEmail(name, email);
        if (loginIdByNameAndEmail.isPresent()) {
            Member member = loginIdByNameAndEmail.get();
            return member.getLoginId();
        } else {
            throw new IllegalArgumentException("해당하는 회원이 없습니다."); // 값을 찾지 못한 경우 예외 처리
        }
    }

    // 로그인
    public boolean login(String loginId, String password) {

        Member member = memberRepository.findByLoginId(loginId);
        if (member != null && member.getPassword().equals(password)) {
            return true;
        }
        return false;

    }


    // 비밀번호 변경을 위한 회원 정보 찾기
    public Member findPasswordByLoginIdAndNameAndEmail(String loginId, String name, String email) {
        Optional<Member> optionalMember = memberRepository.findByLoginIdAndNameAndEmail(loginId, name, email);
        return optionalMember.orElse(null);
    }

    // 회원 탈퇴
    public void withdraw(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        memberRepository.delete(member);
    }

    // 이메일 수정
    public void updateEmail(Long memberId, String newEmail) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member id"));
        member.setEmail(newEmail);
    }

    // 휴대폰 번호 수정
    public void updatePhoneNumber(Long memberId, String newPhoneNumber) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member id"));
        member.setPhone(newPhoneNumber);
    }


}
