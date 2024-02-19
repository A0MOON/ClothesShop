package study.clothesshop.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class CartItem {
    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    // 2.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int quantity; // Adding quantity field with setter

    // Setter method for quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter method for quantity
    public int getQuantity() {
        return quantity;
    }
}
