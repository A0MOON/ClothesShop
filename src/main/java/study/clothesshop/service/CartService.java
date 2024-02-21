package study.clothesshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.clothesshop.domain.Cart;
import study.clothesshop.domain.CartItem;
import study.clothesshop.domain.Item;
import study.clothesshop.domain.Member;
import study.clothesshop.repository.CartRepository;
import study.clothesshop.repository.ItemRepository;
import study.clothesshop.repository.MemberRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

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
        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원의 장바구니가 존재하지 않습니다."));
        return cart.getCartItems();
    }

    // 장바구니 상품 삭제
    public void removeCartItem(Long cartItemId) {
        CartItem cartItem = cartRepository.findCartItemById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 장바구니 상품이 존재하지 않습니다."));

        Cart cart = cartItem.getCart();
        List<CartItem> cartItems = cart.getCartItems();
        if (!cartItems.contains(cartItem)) {
            throw new IllegalArgumentException("해당 장바구니 상품이 존재하지 않습니다.");
        }

        cartItems.remove(cartItem);
        cartRepository.save(cart);
    }




}


