package study.clothesshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import study.clothesshop.domain.*;
import study.clothesshop.exception.NotEnoughStockException;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

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


        //==비즈니스 로직==//
        /**
         * stock 증가
         */
        public void addStock(int quantity) {
            this.stockQuantity += quantity;
        }

        /**
         * stock 감소
         */
        public void removeStock(int quantity) throws NotEnoughStockException {
            int restStock = this.stockQuantity - quantity;
            if (restStock < 0) {
                throw new NotEnoughStockException("need more stock");
            }
            this.stockQuantity = restStock;
        }
    }




