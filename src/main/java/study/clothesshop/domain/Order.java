package study.clothesshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order {

    // 1.
    @Id
    @GeneratedValue
    @Column(name = "orders_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime orderDate;


    // 2.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nonmember_id")
    private NonMember nonmember;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // 3.
    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();





    // 비지니스 메서드

    public static Order createOrder(Member member, Item item, int price, int quantity) {
        Order order = new Order();
        order.setMember(member);

        OrderItem orderItem = OrderItem.createOrderItem(item, price, quantity);
        order.addOrderItem(orderItem);

        order.setOrderStatus(OrderStatus.PAID);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    // method

    public static Order createOrder(Member member) {
        Order order = new Order();
        order.setMember(member);
        return order;
    }

    // OrderItem 추가 메서드
    public void addOrderItem(Item item, int quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setQuantity(quantity);
        orderItem.setOrder(this);
        orderItems.add(orderItem);
    }



}
