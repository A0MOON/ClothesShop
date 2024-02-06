package study.clothesshop.domain;

import study.clothesshop.domain.item.Item;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class RecentlyViewedItem {

    // 1.
    @Id
    @GeneratedValue
    @Column(name = "recently_viewed_id")
    private Long id;
    private LocalDateTime recentlyDate;

    // 2.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}
