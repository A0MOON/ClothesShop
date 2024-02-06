package study.clothesshop.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

// 1.15

@Entity
@Getter
public class NonMember {
    // 1.
    @Id @GeneratedValue
    @Column(name = "nonmember_id")
    private Long id;
    private String nonmemberName;

    // 2.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    // 3.

    @OneToMany(mappedBy = "nonmember")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "nonmember")
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(mappedBy = "nonmember")
    private Order order;




}
