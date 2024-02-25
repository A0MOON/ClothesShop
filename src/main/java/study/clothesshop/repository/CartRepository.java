package study.clothesshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.clothesshop.domain.Cart;
import study.clothesshop.domain.CartItem;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByMemberId(Long memberId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.id = :cartItemId")
    Optional<CartItem> findCartItemById(@Param("cartItemId")Long cartItemId);


}
