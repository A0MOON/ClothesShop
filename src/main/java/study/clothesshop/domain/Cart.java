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

    public CartItem findCartItemByItem(Item item) {
        for (CartItem cartItem : cartItem) {
            if (cartItem.getItem().equals(item)) {
                return cartItem;
            }
        }
        return null;
    }

    public void addCartItem(CartItem cartItem) {
        this.cartItem.add(cartItem);
        }

    public List<CartItem> getCartItems() {
        return cartItem;
    }

    public void removeCartItem(CartItem cartItem) {
        cartItem.getCart().getCartItems().remove(cartItem);
        cartItem.setCart(null);
    }

    public void clearCart() {
        cartItem.clear();
    }



}
