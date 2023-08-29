package com.fragile.ecommercebackend.controller;

import com.fragile.ecommercebackend.exceptions.OrderException;
import com.fragile.ecommercebackend.model.OrderEntity;
import com.fragile.ecommercebackend.response.ApiResponse;
import com.fragile.ecommercebackend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<List<OrderEntity>> getAllOrdersHandler() {
        List<OrderEntity> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<OrderEntity> confirmOrderHandler(@PathVariable("orderId") Long orderId,
                                                           @RequestHeader("Authorization") String jwt) throws OrderException {
        OrderEntity order = orderService.confirmedOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/ship")
    public ResponseEntity<OrderEntity> shippedOrderHandler(@PathVariable("orderId") Long orderId,
                                                           @RequestHeader("Authorization") String jwt) throws OrderException {
        OrderEntity order = orderService.shippedOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderEntity> cancelledOrderHandler(@PathVariable("orderId") Long orderId,
                                                             @RequestHeader("Authorization") String jwt) throws OrderException {
        OrderEntity order = orderService.canceledOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }


    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<OrderEntity> deliveredOrderHandler(@PathVariable("orderId") Long orderId,
                                                             @RequestHeader("Authorization") String jwt) throws OrderException {
        OrderEntity order = orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/delete")
    public ResponseEntity<ApiResponse> deleteOrderHandler(@PathVariable("orderId") Long orderId,
                                                          @RequestHeader("Authorization") String jwt) throws OrderException {
        String message = orderService.deleteOrder(orderId);
        ApiResponse apiResponse = new ApiResponse(message, true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
