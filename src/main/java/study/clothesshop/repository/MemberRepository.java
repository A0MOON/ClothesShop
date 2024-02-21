package study.clothesshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.clothesshop.domain.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findById(Long id);

    Optional<Member> findLoginIdByNameAndEmail(String name, String email);

    Optional<Member> findByLoginIdAndPassword(String loginId, String password);

    Optional<Member> findByNameAndEmailAndLoginIdAndPassword(String name, String email, String loginId, String Password);

    List<Member> findByName(String name);

    Member findByLoginId(String loginId);

    Optional<Member> findByLoginIdAndNameAndEmail(String loginId, String name, String email);


}
