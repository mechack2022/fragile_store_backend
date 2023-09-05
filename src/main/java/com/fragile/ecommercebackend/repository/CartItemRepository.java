package com.fragile.ecommercebackend.repository;

import com.fragile.ecommercebackend.model.Cart;
import com.fragile.ecommercebackend.model.CartItem;
import com.fragile.ecommercebackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

//   @Query("select ci from CartItem ci where ci.product=:product and ci.size=:size and ci.cart=:cart and ci.userId=:userId")
//   CartItem findByCartAndProductAndSizeAndUser(@Param("cart") Cart cart,@Param("product") Product product,@Param("size") String size,@Param("userId")Long userId);

   @Query("SELECT ci FROM CartItem ci WHERE ci.product = :product AND ci.size = :size AND ci.cart = :cart AND ci.userId = :userId")
   CartItem findByCartAndProductAndSizeAndUser(
           @Param("cart") Cart cart,
           @Param("product") Product product,
           @Param("size") String size,
           @Param("userId") Long userId
   );


}
