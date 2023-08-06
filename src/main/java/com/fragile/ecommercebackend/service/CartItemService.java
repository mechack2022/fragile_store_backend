package com.fragile.ecommercebackend.service;

import com.fragile.ecommercebackend.exceptions.CartItemExeption;
import com.fragile.ecommercebackend.exceptions.UserException;
import com.fragile.ecommercebackend.model.Cart;
import com.fragile.ecommercebackend.model.CartItem;
import com.fragile.ecommercebackend.model.Product;

public interface CartItemService {
    CartItem createCartItem(CartItem cartItem);

    CartItem updateCartItem(Long userId, Long cartItemId, CartItem cartItem) throws CartItemExeption, UserException;

    CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    String removeCartItem(Long userId, Long cartItemId) throws CartItemExeption, UserException;

    CartItem findCartItemById(Long cartItemId) throws CartItemExeption;

}
