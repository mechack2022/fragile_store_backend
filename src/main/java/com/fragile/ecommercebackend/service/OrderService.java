package com.fragile.ecommercebackend.service;

import com.fragile.ecommercebackend.exceptions.OrderException;
import com.fragile.ecommercebackend.model.Address;
import com.fragile.ecommercebackend.model.OrderEntity;
import com.fragile.ecommercebackend.model.User;

import java.util.List;

public interface OrderService {

    OrderEntity createOrder(User user, Address shippingAddress);

    OrderEntity findOrderById(Long orderId) throws OrderException;

    List<OrderEntity> userOderHistory(Long userId);

    OrderEntity placeOrder(Long orderId) throws OrderException;
    public OrderEntity confirmedOrder(Long orderId);

    OrderEntity shippedOrder(Long orderId) throws OrderException;

    OrderEntity deliveredOrder(Long orderId) throws OrderException;

    OrderEntity canceledOrder(Long orderId) throws OrderException;

    List<OrderEntity> getAllOrders();

    String deleteOrder(Long orderId) throws OrderException;

}
