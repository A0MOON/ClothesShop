package study.clothesshop.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Coupon {

    // 1.
    @Id
    @GeneratedValue
    @Column(name = "coupon_id")
    private Long id;

    private String code;
    private int couponDiscountAmount;
    private LocalDateTime couponDate;

    // 3.
    @OneToMany(mappedBy = "coupon")
    private List<MemberCoupon> memberCoupon = new ArrayList<>();
}
