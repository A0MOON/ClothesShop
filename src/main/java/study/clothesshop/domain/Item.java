package study.clothesshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Item {
    // 1.
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
    private int discountAmount;
    private String description;

    // 2.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_item_id")
    // @OneToOne(fetch = FetchType.LAZY, mappedBy = "item")
    private AdminItem adminItem;

    // 3.

    @OneToMany(mappedBy = "item")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<WishList> wishLists = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();


    @OneToMany(mappedBy = "item")
    private List<RecentlyViewedItem> recentlyViewedItems = new ArrayList<>();


    //
        public void addStock(int quantity) {
            this.stockQuantity += quantity;
        }

        public void removeStock(int quantity)  {
            int restStock = this.stockQuantity - quantity;
            if (restStock < 0) {
                throw new IllegalArgumentException("Not enough stock");
            }
            this.stockQuantity = restStock;
        }
    }




