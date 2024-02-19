package study.clothesshop.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import study.clothesshop.service.OrderService;

@Entity
@Getter @Setter
public class OrderItem {


    // 1.
    @Id
    @GeneratedValue
    @Column(name = "orderitem_id")
    private Long id;

    private int quantity;
    private int price;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

   private boolean refundCheck;

    // 2.
    @ManyToOne(fetch = FetchType.LAZY) // 흠
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


    //3.

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int
            quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setPrice(orderPrice);
        orderItem.setQuantity(quantity);
        item.removeStock(quantity);
        return orderItem;
    }
    //==비즈니스 로직==//
    /** 주문 취소 */
    public void cancel() {
        getItem().addStock(quantity);
    }
    //==조회 로직==//
    /** 주문상품 전체 가격 조회 */
    public int getTotalPrice() {
        return getPrice() * getQuantity();
    }

}
