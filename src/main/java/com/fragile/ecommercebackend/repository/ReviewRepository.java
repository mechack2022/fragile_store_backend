package com.fragile.ecommercebackend.repository;

import com.fragile.ecommercebackend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository  extends JpaRepository<Review, Long> {
    @Query("select r from Rating r where r.product.id =:productId ")
    List<Review> findReviewsByProductId(@Param("productId") Long productId);
}

