package com.fragile.ecommercebackend.repository;

import com.fragile.ecommercebackend.model.OrderEntity;
import com.fragile.ecommercebackend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {


    @Query("SELECT o FROM OrderEntity o WHERE o.user.id = :userId AND (o.orderStatus = 'PLACE' OR o.orderStatus = 'CONFIRMED' OR o.orderStatus = 'SHIPPED' OR o.orderStatus = 'DELIVERED')")
    public List<OrderEntity> getUsersOrder(@Param("userId") Long userId);

}
