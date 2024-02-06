package study.clothesshop.domain;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class MemberCoupon {

    // 1.
    @Id
    @GeneratedValue
    @Column(name = "member_coupon_id")
    private Long id;

    private boolean couponUsed;
    private LocalDateTime couponUsegeDate;

    // 2.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

}
