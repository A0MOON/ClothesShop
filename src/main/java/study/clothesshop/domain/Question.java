package study.clothesshop.domain;
import study.clothesshop.domain.item.Item;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Question {
    @Id
    @GeneratedValue
    @Column(name = "question_id")
    private Long id;

    private LocalDateTime questionDate;
    private String questionContents;

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
