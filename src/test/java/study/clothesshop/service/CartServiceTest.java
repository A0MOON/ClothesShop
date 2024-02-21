package study.clothesshop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.clothesshop.domain.Cart;
import study.clothesshop.domain.CartItem;
import study.clothesshop.domain.Item;
import study.clothesshop.domain.Member;
import study.clothesshop.repository.CartRepository;
import study.clothesshop.repository.ItemRepository;
import study.clothesshop.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@Rollback(value = false)
class CartServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @Test
    @Rollback(value = false)
    void 장바구니_상품추가() {
        // Given
        Member member = new Member();
        member.setLoginId("testuser");
        member.setName("Test User");
        memberRepository.save(member);

        Item item = new Item();
        item.setName("Test Item");
        item.setPrice(10000);
        item.setStockQuantity(10);
        itemRepository.save(item);

        // When
        cartService.addToCart(member.getId(), item.getId(), 2);

        // Then
        Cart cart = member.getCart();
        assertNotNull(cart);
        assertEquals(1, cart.getCartItems().size());

        CartItem cartItem = cart.getCartItems().get(0);
        assertNotNull(cartItem);
        assertEquals(item, cartItem.getItem());
        assertEquals(2, cartItem.getQuantity());
    }

    @Test
    @Rollback(value = false)
    void 장바구니상품목록조회() {
        // Given
        Member member = new Member();
        member.setLoginId("testuser");
        member.setName("Test User");
        memberRepository.save(member);

        Item item1 = new Item();
        item1.setName("Test Item 1");
        item1.setPrice(10000);
        item1.setStockQuantity(10);
        itemRepository.save(item1);

        Item item2 = new Item();
        item2.setName("Test Item 2");
        item2.setPrice(20000);
        item2.setStockQuantity(5);
        itemRepository.save(item2);

        Cart cart = new Cart();
        cart.setMember(member);
        CartItem cartItem1 = new CartItem();
        cartItem1.setItem(item1);
        cartItem1.setQuantity(2);
        CartItem cartItem2 = new CartItem();
        cartItem2.setItem(item2);
        cartItem2.setQuantity(1);
        cart.addCartItem(cartItem1);
        cart.addCartItem(cartItem2);
        cartRepository.save(cart);

        // When
        List<CartItem> cartItems = cartService.getCartItems(member.getId());

        // Then
        assertNotNull(cartItems);
        assertEquals(2, cartItems.size());
    }

    @Test
    @Rollback(value = false)
    void removeCartItem() {
        // Given
        Member member = new Member();
        member.setLoginId("testuser");
        member.setName("Test User");
        memberRepository.save(member);

        Item item = new Item();
        item.setName("Test Item");
        item.setPrice(10000);
        item.setStockQuantity(10);
        itemRepository.save(item);

        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);

        CartItem cartItem = new CartItem();
        cartItem.setItem(item);
        cartItem.setQuantity(2);
        cart.addCartItem(cartItem);
        cartRepository.save(cart);

        // When
        cartService.removeCartItem(cartItem.getId());

        // Then
        Cart updatedCart = cartRepository.findById(cart.getId())
                .orElseThrow(() -> new RuntimeException("장바구니를 찾을 수 없습니다."));
        assertEquals(0, updatedCart.getCartItems().size());
    }

    @Test
    @Rollback(value = false)
    void updateCartItemQuantity() {
        // Given
        Member member = new Member();
        member.setLoginId("testuser");
        member.setName("Test User");
        memberRepository.save(member);

        Item item = new Item();
        item.setName("Test Item");
        item.setPrice(10000);
        item.setStockQuantity(10);
        itemRepository.save(item);

        Cart cart = new Cart();
        cart.setMember(member);

        CartItem cartItem = new CartItem();
        cartItem.setItem(item);
        cartItem.setQuantity(2);

        cart.addCartItem(cartItem);
        cartRepository.save(cart);

        // When
        int newQuantity = 3;
        cartService.updateCartItemQuantity(cartItem.getId(), newQuantity);

        // Then
        Cart updatedCart = cartRepository.findById(cart.getId())
                .orElseThrow(() -> new RuntimeException("장바구니를 찾을 수 없습니다."));
        CartItem updatedCartItem = updatedCart.getCartItems().get(0);
        assertNotNull(updatedCartItem);
        assertEquals(newQuantity, updatedCartItem.getQuantity());
    }





}
