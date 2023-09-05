package com.fragile.ecommercebackend.controller;

import com.fragile.ecommercebackend.exceptions.CartItemExeption;
import com.fragile.ecommercebackend.exceptions.UserException;
import com.fragile.ecommercebackend.model.Cart;
import com.fragile.ecommercebackend.model.User;
import com.fragile.ecommercebackend.request.AddItemToCatReq;
import com.fragile.ecommercebackend.response.ApiResponse;
import com.fragile.ecommercebackend.service.CartItemService;
import com.fragile.ecommercebackend.service.CartService;
import com.fragile.ecommercebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.IMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//this controller is responsible find user cart and add item to cart
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final UserService userService;

    private final CartService cartService;

    private CartItemService cartItemService;

    @GetMapping("/")
//    @Operation(description="find cart by userId")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());
        log.info("user Id :{}", user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestHeader("Authorization") String jwt, @RequestBody AddItemToCatReq req) {
        User user = userService.findUserProfileByJwt(jwt);
        String message = cartService.addCartItem(user.getId(), req);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(true);
        apiResponse.setMessage(message);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

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


}
