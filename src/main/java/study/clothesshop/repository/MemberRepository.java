package study.clothesshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.clothesshop.domain.Member;
import study.clothesshop.domain.UserStatus;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNameAndEmail(String name, String email);

    Optional<Member> findByLoginIdAndPassword(String loginId, String password);

    Optional<Member> findByNameAndEmailAndLoginIdAndPassword(String name, String email, String loginId, String Password);

    List<Member> findByName(String name);

    Member findByLoginId(String loginId);

    Optional<Member> findMemberByLoginId(String loginId);






}
