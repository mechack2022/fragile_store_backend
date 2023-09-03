package com.fragile.ecommercebackend.service;

import com.fragile.ecommercebackend.exceptions.OrderException;
import com.fragile.ecommercebackend.model.*;
import com.fragile.ecommercebackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    private final OrderRepository orderRepository;
//    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;
//    private final OrderItemService orderItemService;
    private final CartService cartService;

    @Override
    public OrderEntity createOrder(User user, Address shippingAddress) {
        shippingAddress.setUser(user);
        Address address = addressRepository.save(shippingAddress);
        user.getAddress().add(address);
        userRepository.save(user);

        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();

            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setDiscountedPrice(item.getDiscountedPrice());
            orderItem.setUserId(item.getUserId());
            orderItem.setSize(item.getSize());

            OrderItem createdOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(createdOrderItem);
        }

//        create a new order
        OrderEntity createdOrder = new OrderEntity();

        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        createdOrder.setTotalItem(cart.getTotalItem());
        createdOrder.setDiscount(cart.getDiscount());

        createdOrder.setShippingAddress(address);
        createdOrder.getPaymentDetails().setPaymentStatus("PENDING");
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setCreatedAt(LocalDateTime.now());
        createdOrder.setOrderDate(LocalDateTime.now());

        OrderEntity savedOrder = orderRepository.save(createdOrder);
        //save orderItem
        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }

        return savedOrder;
    }

    @Override
    public OrderEntity findOrderById(Long orderId) throws OrderException {
        Optional<OrderEntity> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            return order.get();
        }
        throw new OrderException("order not found with id: " + orderId);
    }

    @Override
    public List<OrderEntity> userOderHistory(Long userId) {
        return orderRepository.getUsersOrder(userId);
    }

    @Override
    public OrderEntity placeOrder(Long orderId) throws OrderException {
        OrderEntity order = findOrderById(orderId);
        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setPaymentStatus("COMPLETED");
        return order;
    }

    @Override
    public OrderEntity shippedOrder(Long orderId) throws OrderException {
        OrderEntity order = findOrderById(orderId);
        order.setOrderStatus("SHIPPED");
        return orderRepository.save(order);
    }

    @Override
    public OrderEntity confirmedOrder(Long orderId) {
        OrderEntity order = findOrderById(orderId);
        order.setOrderStatus("CONFIRMED");
        return orderRepository.save(order);
    }

    @Override
    public OrderEntity deliveredOrder(Long orderId) throws OrderException {
        OrderEntity order = findOrderById(orderId);
        order.setOrderStatus("DELIVERED");
        return orderRepository.save(order);
    }

    @Override
    public OrderEntity canceledOrder(Long orderId) throws OrderException {
        OrderEntity order = findOrderById(orderId);
        order.setOrderStatus("CANCELLED");
        return orderRepository.save(order);
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public String deleteOrder(Long orderId) throws OrderException {
        OrderEntity order = findOrderById(orderId);
        orderRepository.delete(order);
        return "Order deleted successfully";
    }
}
