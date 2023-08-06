package com.fragile.ecommercebackend.service;

import com.fragile.ecommercebackend.exceptions.ProductException;
import com.fragile.ecommercebackend.model.Cart;
import com.fragile.ecommercebackend.request.AddItemToCatReq;
import com.fragile.ecommercebackend.model.User;

public interface CartService  {
    Cart createCart(User user);
    String addCartItem(Long userId, AddItemToCatReq addItemToCartRequest) throws ProductException;
    Cart findUserCart(Long userId);

}
