package study.clothesshop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.clothesshop.domain.Cart;
import study.clothesshop.domain.CartItem;
import study.clothesshop.domain.Item;
import study.clothesshop.domain.Member;
import study.clothesshop.repository.CartRepository;
import study.clothesshop.repository.CartItemRepository;
import study.clothesshop.repository.ItemRepository;
import study.clothesshop.repository.MemberRepository;

import study.clothesshop.service.CartService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;
    @Test
    public void 장바구니_상품추가() {
        //given
        Member member = new Member();
        member.setLoginId("testuser");
        member.setName("Test User");
        member.setPassword("password");
        memberRepository.save(member);

        Cart cart = new Cart();
        member.setCart(cart);
        cart.setMember(member);
        cartRepository.save(cart);

        Item item = new Item();
        item.setName("Test Item");
        item.setPrice(100);
        item.setStockQuantity(10);
        itemRepository.save(item);

        CartItem cartItem = new CartItem();
        cartItem.setItem(item);
        cartItem.setQuantity(2);
        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);

        // when
        cart.getCartItem().add(cartItem);
        cartRepository.save(cart);

        // then
        Optional<Member> optionalMember = memberRepository.findById(member.getId());
        assertTrue(optionalMember.isPresent());
        Member savedMember = optionalMember.get();

        Cart savedCart = savedMember.getCart();
        assertNotNull(savedCart);
        assertEquals(1, savedCart.getCartItem().size());

        CartItem savedCartItem = savedCart.getCartItem().get(0);
        assertNotNull(savedCartItem);
        assertNotNull(savedCartItem.getItem());
        assertEquals(item.getId(), savedCartItem.getItem().getId());
        assertEquals(2, savedCartItem.getQuantity());
    }

    @Test
    public void 장바구니상품_제거() {
        // given
        Member member = new Member();
        member.setLoginId("testuser");
        member.setName("Test User");
        member.setPassword("password");
        memberRepository.save(member);

        Cart cart = new Cart();
        member.setCart(cart);
        cart.setMember(member);
        cartRepository.save(cart);

        Item item = new Item();
        item.setName("Test Item");
        item.setPrice(100);
        item.setStockQuantity(10);
        itemRepository.save(item);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setQuantity(2);
        cartItemRepository.save(cartItem);

        // when
        cartService.removeItemFromCart(cart.getId(), cartItem.getId());
        Optional<Cart> optionalCart = cartRepository.findById(cart.getId());
        assertTrue(optionalCart.isPresent());
        Cart savedCart = optionalCart.get();

        // then
        assertEquals(0, savedCart.getCartItem().size());
    }


}
