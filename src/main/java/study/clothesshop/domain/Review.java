package study.clothesshop.domain;

import study.clothesshop.domain.item.Item;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Review {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    private Rate rate;
    private String reviewContents;
    private LocalDateTime reviewDate;

    // 2.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nonmember_id")
    private NonMember nonmember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
