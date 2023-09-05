package com.fragile.ecommercebackend.controller;

import com.fragile.ecommercebackend.exceptions.CartItemExeption;
import com.fragile.ecommercebackend.exceptions.UserException;
import com.fragile.ecommercebackend.model.CartItem;
import com.fragile.ecommercebackend.model.User;
import com.fragile.ecommercebackend.response.ApiResponse;
import com.fragile.ecommercebackend.service.CartItemService;
import com.fragile.ecommercebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cartItem/")
@RequiredArgsConstructor
public class CartItemController {

    private final UserService userService;

    private final CartItemService cartItemService;

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deletedCartItemHandler(@RequestHeader("Authorization") String jwt, @PathVariable("cartItemId") Long cartItemId)
            throws CartItemExeption, UserException {
        User user = userService.findUserProfileByJwt(jwt);
        String message = cartItemService.removeCartItem(user.getId(), cartItemId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(true);
        apiResponse.setMessage(message);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemHandler(@RequestHeader("Authorization") String jwt, @PathVariable("cartItemId") Long cartItemId, @RequestBody CartItem cartItem)
            throws CartItemExeption, UserException {
        User user = userService.findUserProfileByJwt(jwt);
        CartItem updateCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
        return new ResponseEntity<>(updateCartItem, HttpStatus.OK);
    }

}
