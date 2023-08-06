package com.fragile.ecommercebackend.service;

import com.fragile.ecommercebackend.exceptions.CartItemExeption;
import com.fragile.ecommercebackend.exceptions.UserException;
import com.fragile.ecommercebackend.model.Cart;
import com.fragile.ecommercebackend.model.CartItem;
import com.fragile.ecommercebackend.model.Product;
import com.fragile.ecommercebackend.model.User;
import com.fragile.ecommercebackend.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserService userService;

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        CartItem newCartItem = CartItem.
                builder()
                .quantity(1)
                .Price(cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .discountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity())
                .build();
        return cartItemRepository.save(newCartItem);
    }

    @Override
    public CartItem updateCartItem(Long userId, Long cartItemId, CartItem cartItem) throws CartItemExeption, UserException {
        CartItem item = findCartItemById(cartItemId);
        User user = userService.findUserById(item.getUserId());
        if (user.getId().equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getProduct().getPrice() * item.getQuantity());
            item.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * item.getQuantity());
        }
        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        return cartItemRepository.findByCartAndProductAndSizeAndUser(cart, product, size, userId);
    }

    @Override
    public String removeCartItem(Long userId, Long cartItemId) throws CartItemExeption, UserException {
        CartItem cartItem = findCartItemById(cartItemId);
        User cartUser = userService.findUserById(cartItem.getUserId());
        User reqUser = userService.findUserById(userId);
        if (cartUser.equals(reqUser)) {
            cartItemRepository.delete(cartItem);
            return "cartItem :" + cartItem.getProduct().getTitle() + " deleted successfully. ";
        } else {
            throw new UserException("You are not allow to remove other user's item. ");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemExeption {
        Optional<CartItem> item = cartItemRepository.findById(cartItemId);
        if (item.isPresent()) {
            return item.get();
        } else {
            throw new CartItemExeption("Item not found with the id: " + cartItemId);
        }
    }
}
