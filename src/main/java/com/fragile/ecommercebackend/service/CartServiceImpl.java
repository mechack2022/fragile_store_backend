package com.fragile.ecommercebackend.service;

import com.fragile.ecommercebackend.exceptions.ProductException;
import com.fragile.ecommercebackend.model.Cart;
import com.fragile.ecommercebackend.model.CartItem;
import com.fragile.ecommercebackend.model.Product;
import com.fragile.ecommercebackend.repository.CartRepository;
import com.fragile.ecommercebackend.request.AddItemToCatReq;
import com.fragile.ecommercebackend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CartItemService cartItemService;

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemToCatReq addItemToCartRequest) throws ProductException {
        Cart cart = cartRepository.findCartByUserId(userId);
        Product product = productService.findProductById(addItemToCartRequest.getProductId());
        //check if item to add is present
        CartItem isPresent = cartItemService.isCartItemExist(cart, product, addItemToCartRequest.getSize(), userId);
        if (isPresent == null) {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setUserId(userId);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(addItemToCartRequest.getQuantity());
            newCartItem.setSize(addItemToCartRequest.getSize());

            int itemPrice = product.getDiscountedPrice() * addItemToCartRequest.getQuantity();
            newCartItem.setPrice(itemPrice);

            CartItem createdCartItem = cartItemService.createCartItem(newCartItem);

            cart.getCartItems().add(createdCartItem);
        }
        return "Item added to cart successfully.";
    }


    @Override
    public Cart findUserCart(Long userId) {
        Cart cart = cartRepository.findCartByUserId(userId);

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        for (CartItem cartItem : cart.getCartItems()) {
            totalPrice = totalItem + cartItem.getPrice();
            totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
            totalItem = totalItem + cartItem.getQuantity();
        }
        cart.setTotalPrice((double) totalPrice);
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setDiscount(totalPrice - totalDiscountedPrice);

        return cart;
    }


}
