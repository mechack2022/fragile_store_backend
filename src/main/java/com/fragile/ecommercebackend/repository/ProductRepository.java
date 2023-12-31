package com.fragile.ecommercebackend.repository;

import com.fragile.ecommercebackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
//    @Query("SELECT p FROM Product p " +
//            "WHERE(p.category.name =:category OR :category='') " +
//            "AND ((:minPrice IS NULL OR :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) " +
//            "AND (:minDiscount IS NULL OR p.discountPercent >= :minDiscount) " +
//            "ORDER BY " +
//            "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, " +
//            "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC")

    @Query("SELECT p FROM Product p\n" +
            "WHERE (:category IS NULL OR p.category.name = :category)\n" +
            "AND ((:minPrice IS NULL OR :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice))\n" +
            "AND (:minDiscount IS NULL OR p.discountPercent >= :minDiscount)\n" +
            "ORDER BY\n" +
            "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC,\n" +
            "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC\n")
    List<Product> findProductsByCategoryAndPriceRangeAndDiscount(
            @Param("category") String category,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("minDiscount") Integer minDiscount,
            @Param("sort") String sort
    );


}
