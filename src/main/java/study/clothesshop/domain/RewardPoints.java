package study.clothesshop.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RewardPoints {
    // 1.
    @Id
    @GeneratedValue
    @Column(name = "reward_id")
    private Long rewardId;
    private int rewardAmount;
    @Enumerated(EnumType.STRING)
    private RewardStatus rewardStatus;

    //2.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
