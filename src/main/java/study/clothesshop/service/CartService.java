package study.clothesshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.clothesshop.domain.*;
import study.clothesshop.repository.CartRepository;
import study.clothesshop.repository.ItemRepository;
import study.clothesshop.repository.MemberRepository;
import study.clothesshop.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    // 장바구니 상품 추가
    public void addToCart(Long memberId, Long itemId, int quantity) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));

        Cart cart = member.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setMember(member);
            member.setCart(cart);
        }

        CartItem cartItem = cart.findCartItemByItem(item);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setItem(item);
            cartItem.setQuantity(quantity);
            cart.addCartItem(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartRepository.save(cart);
    }

    // 장바구니 상품 목록 조회
    public List<CartItem> getCartItems(Long memberId) {
        Cart cart = getCartByMemberId(memberId);
        return cart.getCartItems();
    }

    // 장바구니 상품 삭제
    @Transactional
    public void removeCartItem(Long cartItemId) {
        CartItem cartItem = getCartItemById(cartItemId);
        Cart cart = cartItem.getCart();
        cart.removeCartItem(cartItem);
        cartRepository.save(cart);
    }

    // 장바구니 상품 수량 수정
    public void updateCartItemQuantity(Long cartItemId, int newQuantity) {
        Optional<CartItem> optionalCartItem = cartRepository.findCartItemById(cartItemId);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(newQuantity);
            cartRepository.save(cartItem.getCart());
        } else {
            // 장바구니 상품이 존재하지 않으면 새로운 장바구니 상품을 추가
            throw new IllegalArgumentException("해당 장바구니 상품이 존재하지 않습니다.");
        }
    }

    // 회원 ID로 장바구니 조회
    private Cart getCartByMemberId(Long memberId) {
        return cartRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원의 장바구니가 존재하지 않습니다."));
    }

    // 장바구니 상품 ID로 장바구니 상품 조회
    private CartItem getCartItemById(Long cartItemId) {
        return cartRepository.findCartItemById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 장바구니 상품이 존재하지 않습니다."));
    }

    // 장바구니에 담긴 상품 주문하기
    public Order orderCartItems(Long memberId) {
        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원의 장바구니가 존재하지 않습니다."));

        // 장바구니에 상품이 없는 경우
        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("장바구니에 상품이 없습니다.");
        }

        Member member = cart.getMember();

        Order order = Order.createOrder(member);

        for (CartItem cartItem : cart.getCartItems()) {
            order.addOrderItem(cartItem.getItem(), cartItem.getQuantity());

            // 주문한 상품의 재고를 감소시킴
            Item item = cartItem.getItem();
            item.removeStock(cartItem.getQuantity());
            itemRepository.save(item);
        }

        order.setOrderDate(LocalDateTime.now());

        orderRepository.save(order);

        // 장바구니 비우기
        cart.clearCart();

        return order;
    }





}


