package study.clothesshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.context.annotation.Lazy;

@Entity
@Getter
public class MemberWishList {
    // 1.
    @Id
    @GeneratedValue
    @Column(name = "member_wishlist_id")
    private Long id;

    // 2.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishlist_id")
    private WishList wishlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
