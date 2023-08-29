package com.fragile.ecommercebackend.repository;

import com.fragile.ecommercebackend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {
   @Query("select c from Cart c where c.user.id=:userId")
    Cart findCartByUserId(@Param("userId") Long userId);
}
