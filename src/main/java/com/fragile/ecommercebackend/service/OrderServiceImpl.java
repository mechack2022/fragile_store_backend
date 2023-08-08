package com.fragile.ecommercebackend.service;

import com.fragile.ecommercebackend.exceptions.OrderException;
import com.fragile.ecommercebackend.model.Address;
import com.fragile.ecommercebackend.model.OrderEntity;
import com.fragile.ecommercebackend.model.User;
import com.fragile.ecommercebackend.repository.CartRepository;
import com.fragile.ecommercebackend.repository.ProductRepository;
import com.fragile.ecommercebackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Override
    public OrderEntity createOrder(User user, Address shippingAddress) {
        return null;
    }

    @Override
    public OrderEntity findOrderById(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public List<OrderEntity> userOderHistory(Long userId) {
        return null;
    }

    @Override
    public OrderEntity placeOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public OrderEntity shippedOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public OrderEntity deliveredOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public OrderEntity canceledOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return null;
    }

    @Override
    public String deleteOrder(Long orderId) throws OrderException {
        return null;
    }
}
