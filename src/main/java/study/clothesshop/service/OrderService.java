package study.clothesshop.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.clothesshop.domain.*;
import study.clothesshop.repository.ItemRepository;
import study.clothesshop.repository.MemberRepository;
import study.clothesshop.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    // 상품 주문
    public Long order(Long memberId, Long itemId, int quantity) {
        // 회원 조회
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        // 상품 조회
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));
        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), quantity);

        // 주문 생성
        Order order = Order.createOrder(member, item, item.getPrice(), quantity);
        order.addOrderItem(orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    // 주문 내역 조회
    public List<Order> getOrdersForMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        return orderRepository.findByMember(member);
    }

    // 주문 결제
    public void processPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다."));

        order.setOrderStatus(OrderStatus.PAID);

        orderRepository.save(order);
    }

    // 주문 결제 내역 조회
    public String getOrderPaymentStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다."));

        return order.getOrderStatus().toString();
    }





}
