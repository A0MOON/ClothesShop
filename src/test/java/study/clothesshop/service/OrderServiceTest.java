package study.clothesshop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.junit.jupiter.api.Test;
import study.clothesshop.domain.*;
import study.clothesshop.repository.ItemRepository;
import study.clothesshop.repository.MemberRepository;
import study.clothesshop.repository.OrderRepository;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@Transactional
@Rollback(value = false) // unique index or primary key violation
public class OrderServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Test
    void 상품주문() {
        // Given
        Member member = new Member();
        member.setLoginId("testuser1");
        member.setName("Test Use1");
        memberRepository.save(member);

        Item item = new Item();
        item.setName("Test Item1");
        item.setPrice(10000);
        item.setStockQuantity(10);
        itemRepository.save(item);

        // When
        Long orderId = orderService.order(member.getId(), item.getId(), 2);

        // Then
        assertNotNull(orderId);
        Order savedOrder = orderRepository.findById(orderId).orElse(null);
        assertNotNull(savedOrder);
    }

    @Test
    void 주문내역조회() {
        // Given
        Member member = new Member();
        member.setLoginId("testuser2");
        member.setName("Test User2");
        memberRepository.save(member);

        Item item = new Item();
        item.setName("Test Item2");
        item.setPrice(10000);
        item.setStockQuantity(10);
        itemRepository.save(item);

        Order order1 = Order.createOrder(member, item, item.getPrice(), 2);
        orderRepository.save(order1);

        Order order2 = Order.createOrder(member, item, item.getPrice(), 1);
        orderRepository.save(order2);

        // When
        List<Order> orders = orderService.getOrdersForMember(member.getId());

        // Then
        assertNotNull(orders);
        assertEquals(2, orders.size());
    }

    @Test
    void 상품주문결제() {
        // Given
        Member member = new Member();
        member.setLoginId("testuser3");
        member.setName("Test User3");
        memberRepository.save(member);

        Item item = new Item();
        item.setName("Test Item3");
        item.setPrice(10000);
        item.setStockQuantity(10);
        itemRepository.save(item);

        Order order = Order.createOrder(member, item, item.getPrice(), 2);
        orderRepository.save(order);

        // When
        orderService.processPayment(order.getId());

        // Then
        Order paidOrder = orderRepository.findById(order.getId()).orElse(null);
        assertNotNull(paidOrder);
        assertEquals(paidOrder.getOrderStatus(), OrderStatus.PAID);
    }

    @Test
    void 주문결제내역조회() {
        // Given
        Member member = new Member();
        member.setLoginId("testuser4");
        member.setName("Test User4");
        memberRepository.save(member);

        Item item = new Item();
        item.setName("Test Item4");
        item.setPrice(10000);
        item.setStockQuantity(10);
        itemRepository.save(item);

        Order order = Order.createOrder(member, item, item.getPrice(), 2);
        order.setOrderStatus(OrderStatus.PAID);
        orderRepository.save(order);

        // When
        String paymentStatus = orderService.getOrderPaymentStatus(order.getId());

        // Then
        assertNotNull(paymentStatus);
        assertEquals("PAID", paymentStatus);
    }





}

