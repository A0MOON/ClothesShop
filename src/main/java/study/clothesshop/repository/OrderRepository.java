package study.clothesshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.clothesshop.domain.Member;
import study.clothesshop.domain.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findById(Long id);

    List<Order> findAll();


    List<Order> findByMemberId(Long memberId);


    List<Order> findByMember(Member member);
}
