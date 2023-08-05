package com.fragile.ecommercebackend.repository;

import com.fragile.ecommercebackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where (p.category.name=:category or :category='') " +
            "and ((:minPrice is null and :maxPrice is null) or (p.discountedPrice between :minPrice and :maxPrice))"+
            "and (:minDiscount is null or p.discountedPercent >=:minDiscount )" +
            "order by "+
            "case when : sort='price_low' then p.discountedPrice end asc, case when : sort='price_high' then p.discountedPrice end desc"
    )
    List<Product> filterProduct(@Param("category") String category,
                                @Param("maxPrice") Integer maxPrice,
                                @Param("minPrice") Integer minPrice,
                                @Param("minDiscount") Integer  minDiscount,
                                @Param("sort") String sort);
}
