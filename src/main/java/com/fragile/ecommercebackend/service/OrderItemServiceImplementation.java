package com.fragile.ecommercebackend.service;

import com.fragile.ecommercebackend.model.OrderItem;
import com.fragile.ecommercebackend.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderItemServiceImplementation implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
