package study.clothesshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.clothesshop.domain.Cart;
import study.clothesshop.domain.CartItem;
import study.clothesshop.domain.Item;
import study.clothesshop.repository.CartRepository;
import study.clothesshop.repository.CartItemRepository;
import study.clothesshop.repository.ItemRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;



    @Transactional
    public void addItemToCart(Long cartId, Item item, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        CartItem cartItem = cart.findCartItemByItem(item);

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setItem(item);
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
            cart.addCartItem(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Transactional
    public void removeItemFromCart(Long cartId, Long cartItemId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new IllegalArgumentException("Cart Item not found"));

        cart.removeCartItem(cartItem);
        cartItemRepository.delete(cartItem);
    }

    public List<CartItem> listCartItems(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        return cart.getCartItems();
    }

    @Transactional
    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        cart.clearCart();
        cartRepository.save(cart);
    }


    @Transactional
    public void addToCart(Long cartId, Long itemId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with id: " + cartId));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + itemId));

        CartItem cartItem = cart.findCartItemByItem(item);
        if (cartItem != null) {
            // Item is already in the cart, update the quantity
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            // Item is not in the cart, add as a new CartItem
            cartItem = new CartItem();
            cartItem.setItem(item);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem); // Make sure to add the new CartItem to the Cart's collection
        }

        cartRepository.save(cart); // Persist changes to the database
    }
    // Additional methods to calculate total price and other cart operations can be added here
}
