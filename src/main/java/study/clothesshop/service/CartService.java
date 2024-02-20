package study.clothesshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.clothesshop.domain.Cart;
import study.clothesshop.domain.CartItem;
import study.clothesshop.domain.Item;
import study.clothesshop.domain.Member;
import study.clothesshop.repository.CartRepository;
import study.clothesshop.repository.CartItemRepository;
import study.clothesshop.repository.ItemRepository;
import study.clothesshop.repository.MemberRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;

    public void addItemToCart(Long memberId, Long itemId, int quantity) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("Item not found"));

        Cart cart = member.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setMember(member);
            cartRepository.save(cart);
        }

        // 상품이 장바구니에 있는지 체크
        boolean itemAlreadyInCart = cart.getCartItem().stream()
                .anyMatch(cartItem -> cartItem.getItem().getId().equals(itemId));

        if (itemAlreadyInCart) {
            // 상품이 장바구니에 있다면 수량 1 증가
            CartItem existingCartItem = cart.getCartItem().stream()
                    .filter(cartItem -> cartItem.getItem().getId().equals(itemId))
                    .findFirst().orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
        } else {
            // 상품이 카트에 없을 때
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setItem(item);
            cartItem.setQuantity(quantity);
            cart.getCartItem().add(cartItem);
        }

        cartRepository.save(cart);
    }

    @Transactional
    public void removeItemFromCart(Long cartId, Long cartItemId) {
        // Check if the cart exists
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (!optionalCart.isPresent()) {
            throw new IllegalArgumentException("Cart not found with id: " + cartId);
        }
        Cart cart = optionalCart.get();

        // Check if the cart item exists
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
        if (!optionalCartItem.isPresent()) {
            throw new IllegalArgumentException("Cart item not found with id: " + cartItemId);
        }
        CartItem cartItem = optionalCartItem.get();

        // Check if the cart contains the cart item
        if (!cart.getCartItem().contains(cartItem)) {
            throw new IllegalArgumentException("Cart item not found in the cart");
        }

        // Remove the cart item from the cart and update the cart
        cart.getCartItem().remove(cartItem);
        cartRepository.save(cart);
    }




}
