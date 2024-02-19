package study.clothesshop.domain;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Cart {

    // 1.
    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    private int quantity;
    private int totalPrice;
    // 2.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 3.
    @OneToOne(mappedBy = "cart")
    private NonMember nonmember;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItem = new ArrayList<>();




    public void setMember(Member member) {
        this.member = member;
    }
}
