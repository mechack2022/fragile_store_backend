package com.fragile.ecommercebackend.controller;

import com.fragile.ecommercebackend.exceptions.UserException;
import com.fragile.ecommercebackend.model.Address;
import com.fragile.ecommercebackend.model.OrderEntity;
import com.fragile.ecommercebackend.model.User;
import com.fragile.ecommercebackend.service.OrderService;
import com.fragile.ecommercebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<OrderEntity> createOrderHandler(@RequestBody Address shippingAddress, @RequestHeader("Authorization")String jwt)
    throws UserException {
        User foundUser = userService.findUserProfileByJwt(jwt);
        OrderEntity order = orderService.createOrder(foundUser, shippingAddress);
        return new ResponseEntity<>(order, HttpStatus.CREATED);

    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderEntity>> orderUserHistoryHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        User foundUser = userService.findUserProfileByJwt(jwt);
        List<OrderEntity> userOrders =  orderService.userOderHistory(foundUser.getId());
        return new ResponseEntity<>(userOrders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderEntity> findOrderByIdHandler(@RequestHeader("Authorization") String jwt,
                                                                  @PathVariable("orderId") Long orderId){
        User foundUser = userService.findUserProfileByJwt(jwt);
        OrderEntity order = orderService.findOrderById(orderId);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
