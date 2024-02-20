package study.clothesshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.clothesshop.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
