package study.clothesshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.clothesshop.domain.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
