package study.clothesshop.domain;
import study.clothesshop.domain.item.Item;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class WishList {
    // 1.
    @Id
    @GeneratedValue
    @Column(name = "wishlist_id")
    private Long id;

    //2.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // 3.
    @OneToMany(mappedBy = "wishlist")
    List<MemberWishList> memberWishLists = new ArrayList<>();

}
